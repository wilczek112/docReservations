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
//@Controller
//public class VpnController {
//    @Autowired
//    private VpnRepository vpnRepository;
//
//    @GetMapping(path = "/Vpn")
//    public String getVpn(Model model){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        boolean hasUserRole = authentication.getAuthorities().stream()
//                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
//        List<Vpn> vpns = vpnRepository.findAll();
//        List<Vpn> vpnUser = new ArrayList<>();
//        for(int i=0;i<vpns.size();i++){
//            if(vpns.get(i).getRole_id()==2) vpnUser.add(vpns.get(i));
//        }
//
//        if(hasUserRole==true) model.addAttribute("vpn",vpns);
//        else model.addAttribute("vpn", vpnUser);
//        return "vpn";
//    }
////    @GetMapping(path = "/getServers")
////    public @ResponseBody Iterable<Server> getServers(){
////        //model.addAttribute("servery", serverRepository.findAll());
////        return serverRepository.findAll();
////    }
//
//}
//
