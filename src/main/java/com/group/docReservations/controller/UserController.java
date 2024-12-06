package com.group.docReservations.controller;

import com.group.docReservations.classes.User;
import com.group.docReservations.exception.ResourceNotFoundException;
import com.group.docReservations.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // Remove RestTemplate since we're no longer making REST calls

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Display the list of users.
     */
    @GetMapping
    public String listUsers(Model model, Authentication authentication) {
        // Check if the user is an admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            // If not admin, redirect or show an error
            return "redirect:/panel?error=accessDenied";
        }

        List<User> users = userService.findAllUsers();

        // Prepare the data for the 'base-crud' template
        model.addAttribute("title", "Lista użytkowników");
        model.addAttribute("headers", Arrays.asList("Login", "Imię", "Nazwisko", "Email", "Telefon", "Rola"));
        model.addAttribute("fields", Arrays.asList("login", "firstName", "lastName", "email", "phone", "role"));
        model.addAttribute("entities", users);
        model.addAttribute("editUrl", "/users/edit");
        model.addAttribute("deleteUrl", "/users/delete");
        model.addAttribute("addUrl", "/users/add");

        return "base-crud"; // Using your generic 'base-crud' template
    }

    /**
     * Display the form for adding a new user.
     */
    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("title", "Dodaj użytkownika");
        model.addAttribute("fields", Arrays.asList(
                Map.of("name", "login", "label", "Login", "type", "text", "placeholder", "Wpisz login"),
                Map.of("name", "password", "label", "Hasło", "type", "text", "placeholder", "Wpisz hasło"),
                Map.of("name", "confirmPassword", "label", "Potwierdź Hasło", "type", "text", "placeholder", "Potwierdź hasło"),
                Map.of("name", "firstName", "label", "Imię", "type", "text", "placeholder", "Wpisz imię"),
                Map.of("name", "lastName", "label", "Nazwisko", "type", "text", "placeholder", "Wpisz nazwisko"),
                Map.of("name", "email", "label", "Email", "type", "text", "placeholder", "Wpisz email"),
                Map.of("name", "phone", "label", "Telefon", "type", "text", "placeholder", "Wpisz telefon"),
                Map.of("name", "role", "label", "Rola", "type", "text", "placeholder", "Wpisz rolę")
        ));
        model.addAttribute("entity", new User());
        model.addAttribute("actionUrl", "/users/add");
        return "base-form";
    }


    /**
     * Handle the form submission for adding a new user.
     */
    @PostMapping("/add")
    public String addUser(@Valid @ModelAttribute("entity") User user, BindingResult result, Model model) {
        // Validate user input
        if (result.hasErrors()) {
            model.addAttribute("title", "Dodaj użytkownika");
            model.addAttribute("fields", Arrays.asList("login", "password", "confirmPassword", "firstName", "lastName", "email", "phone", "role"));
            model.addAttribute("actionUrl", "/users/add");
            return "base-form";
        }
        // Check for existing login or email
        User existingLogin = userService.findUserByLogin(user.getLogin());
        User existingEmail = userService.findUserByEmail(user.getEmail());

        if (existingLogin != null) {
            result.rejectValue("login", null, "Istnieje już użytkownik o tym loginie.");
        }
        if (existingEmail != null) {
            result.rejectValue("email", null, "Istnieje już użytkownik o tym emailu.");
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            result.rejectValue("confirmPassword", null, "Hasła nie są takie same.");
        }
        if (result.hasErrors()) {
            model.addAttribute("title", "Dodaj użytkownika");
            model.addAttribute("fields", Arrays.asList("login", "password", "confirmPassword", "firstName", "lastName", "email", "phone", "role"));
            model.addAttribute("actionUrl", "/users/add");
            return "base-form";
        }

        try {
            userService.saveUser(user);
            model.addAttribute("message", "Użytkownik został dodany pomyślnie.");
            model.addAttribute("messageType", "success");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Nie udało się dodać użytkownika. Spróbuj ponownie.");
            model.addAttribute("messageType", "error");
            model.addAttribute("title", "Dodaj użytkownika");
            model.addAttribute("fields", Arrays.asList("login", "password", "confirmPassword", "firstName", "lastName", "email", "phone", "role"));
            model.addAttribute("actionUrl", "/users/add");
            return "base-form";
        }
        return "redirect:/users";
    }

    /**
     * Display the form for editing an existing user.
     */
    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable String id, Model model) {
        User user = userService.findUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        model.addAttribute("title", "Edytuj użytkownika");
        model.addAttribute("fields", Arrays.asList("login", "firstName", "lastName", "email", "phone", "role"));
        model.addAttribute("entity", user);
        model.addAttribute("actionUrl", "/users/edit/" + id);
        return "base-form"; // Reuse the same 'base-form' template
    }

    /**
     * Handle the form submission for editing an existing user.
     */
    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable String id, @Valid @ModelAttribute("entity") User user, BindingResult result, Model model) {
        System.out.println("Received User object: " + user);
        if (result.hasErrors()) {
            System.out.println("Validation errors found:");
            result.getAllErrors().forEach(error -> System.out.println(error));
            model.addAttribute("title", "Edytuj użytkownika");
            model.addAttribute("fields", Arrays.asList("login", "firstName", "lastName", "email", "phone", "role"));
            model.addAttribute("actionUrl", "/users/edit/" + id);
            return "base-form";
        }

        try {
            // Ensure the user object has the correct ID
            user.setId(id); // Explicitly set the ID
            userService.updateUser(user);
            model.addAttribute("message", "Użytkownik został zaktualizowany pomyślnie.");
            model.addAttribute("messageType", "success");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Nie udało się zaktualizować użytkownika. Spróbuj ponownie.");
            model.addAttribute("messageType", "error");
            model.addAttribute("title", "Edytuj użytkownika");
            model.addAttribute("fields", Arrays.asList("login", "firstName", "lastName", "email", "phone", "role"));
            model.addAttribute("actionUrl", "/users/edit/" + id);
            return "base-form";
        }
        return "redirect:/users";
    }


    /**
     * Delete a user by ID.
     */
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id, Model model) {
        try {
            userService.deleteUser(id);
            model.addAttribute("message", "Użytkownik został usunięty pomyślnie.");
            model.addAttribute("messageType", "success");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Nie udało się usunąć użytkownika. Spróbuj ponownie.");
            model.addAttribute("messageType", "error");
        }
        return "redirect:/users";
    }
}
