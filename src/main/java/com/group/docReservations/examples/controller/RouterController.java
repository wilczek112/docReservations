package com.group.docReservations.examples.controller;//package com.group.docReservations.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Controller
//public class RouterController {
//        @Autowired
//        private RouterRepository routerRepository;
//
//        @GetMapping(path = "/routers")
//        public String getRouters(Model model) {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            boolean hasUserRole = authentication.getAuthorities().stream()
//                    .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
//            List<Router> routers = routerRepository.findAll();
//            List<Router> routersUser = new ArrayList<>();
//            for (int i = 0; i < routers.size(); i++) {
//                if (routers.get(i).getRole_id() == 2) routersUser.add(routers.get(i));
//            }
//
//            if (hasUserRole == true) model.addAttribute("routers", routers);
//            else model.addAttribute("routers", routersUser);
//            return "router";
//        }
//    }
//
