package com.group.docReservations.examples.controller;//package com.group.docReservations.controller;
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
//public class RdpController {
//    @Autowired
//    private RdpRepository rdpRepository;
//    @GetMapping(path = "/rdp")
//    public String getRdp(Model model){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        boolean hasUserRole = authentication.getAuthorities().stream()
//                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
//        List<Rdp> rdps = rdpRepository.findAll();
//        List<Rdp> rdpUser = new ArrayList<>();
//        for(int i=0;i<rdps.size();i++){
//            if(rdps.get(i).getRole_id()==2) rdpUser.add(rdps.get(i));
//        }
//
//        if(hasUserRole==true) model.addAttribute("servery",rdps);
//        else model.addAttribute("rdps", rdpUser);
//        return "rdp";
//    }
////    @GetMapping(path = "/getServers")
////    public @ResponseBody Iterable<Server> getServers(){
////        //model.addAttribute("servery", serverRepository.findAll());
////        return serverRepository.findAll();
////    }
//
//}
