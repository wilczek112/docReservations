package com.group.docReservations.examples.controller.OLD;//package com.wilk.group.Project_web_admin.controller;
//
//import com.wilk.group.Project_web_admin.ScheduledTasks;
//import com.wilk.group.Project_web_admin.classes.Role;
//import com.wilk.group.Project_web_admin.classes.Server;
//import com.wilk.group.Project_web_admin.classes.User;
//import com.wilk.group.Project_web_admin.repository.RoleRepository;
//import com.wilk.group.Project_web_admin.repository.ServerRepository;
//import com.wilk.group.Project_web_admin.services.UserService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
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
//@Controller
//public class ServerController {
//    @Autowired
//    private ServerRepository serverRepository;
//
//    @GetMapping(path = "/panel/servers")
//    public String getServers(Model model){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        boolean hasUserRole = authentication.getAuthorities().stream()
//                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
//        List<Server> servers = serverRepository.findAll();
//        List<Server> serverUser = new ArrayList<>();
//        for(int i=0;i<servers.size();i++){
//            if(servers.get(i).getRole_id()==2) serverUser.add(servers.get(i));
//        }
//
//        if(hasUserRole==true) model.addAttribute("servery",servers);
//        else model.addAttribute("servery", serverUser);
//        return "serwer";
//    }
////    @GetMapping(path = "/getServers")
////    public @ResponseBody Iterable<Server> getServers(){
////        //model.addAttribute("servery", serverRepository.findAll());
////        return serverRepository.findAll();
////    }
//
//}
