package com.group.docReservations.examples.controller.OLD;//package com.wilk.group.Project_web_admin.controller;
//
//import com.wilk.group.Project_web_admin.services.CustomUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//
//import org.springframework.web.bind.annotation.*;
//@Controller // This means that this class is a Controller
//
//public class MainController {
//
//    @Autowired
//    CustomUserDetailsService customUserDetailsService;
//
//    @RequestMapping(path="/")
//    public String index(Model model) {
//        model.addAttribute("user", new UserDto());
//        return "html/index";
//    }
//    @PostMapping(value = "/login")
//    public String login(@Valid @ModelAttribute("user") UserDto newUser, BindingResult errors, Model model){
//        customUserDetailsService.loadUserByUsername(newUser.getLogin());
//        if (errors.hasErrors()) {
//            System.out.println(errors.getAllErrors());
//            return "/html/register";
//        }else {
//            return "redirect:login?success";
//        }
//    }
//    @GetMapping("/login")
//    public String loginForm() {
//        return "html/panel";
//    }
//
//    @RequestMapping(path="/panel")
//    public String home(){
//        return "html/panel";
//    }
//    @RequestMapping(path="/router")
//    public String router(){
//        return "html/router";
//    }
//    @RequestMapping(path="/database")
//    public String database(){
//        return "html/database";
//    }
//}
