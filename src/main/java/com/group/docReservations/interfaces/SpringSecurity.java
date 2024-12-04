package com.group.docReservations.config;

import com.group.docReservations.services.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication Provider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // Security Filter Chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) -> authorize
                        // Public endpoints
                        .requestMatchers("/register/**", "/saveUser", "/index", "/favicon.ico", "/css/**", "/images/**", "/error/**").permitAll()

                        // Secure `/panel/**` to authenticated users with roles `USER` or `ADMIN`
                        .requestMatchers("/panel/**").hasAnyRole("USER", "ADMIN")

                        // Secure admin endpoints
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Secure API endpoints for Pacjent CRUD operations
                        .requestMatchers("/api/pacjents/**").hasRole("ADMIN")

                        // Any other request requires authentication
                        .anyRequest().authenticated()
                )
                .formLogin(
                        form -> form
                                .loginPage("/index") // Update if your login page is at /index
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/panel", true)
                                .permitAll()
                )
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/index")
                                .permitAll()
                )
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());

        return http.build();
    }

    // Configure AuthenticationManagerBuilder
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    // Access Denied Handler
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}
