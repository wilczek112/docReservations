package com.group.docReservations.examples.controller;//package com.group.docReservations.controller;
//
//import com.wilk.group.Project_web_admin.classes.*;
//import com.wilk.group.Project_web_admin.repository.*;
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
//public class defaultController {
//
//    @GetMapping(path = "/panel/vpn")
//    public String getVpn(Model model){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        boolean hasUserRole = authentication.getAuthorities().stream()
//                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
//        List<Vpn> vpn = vpnRepository.findAll();
//        List<Vpn> vpnUser = new ArrayList<>();
//        for(int i=0;i<vpn.size();i++){
//            if(vpn.get(i).getRole_id()==2) vpnUser.add(vpn.get(i));
//        }
//
//        if(hasUserRole==true) model.addAttribute("vpn",vpn);
//        else model.addAttribute("vpn", vpnUser);
//        model.addAttribute("auth", hasUserRole);
//        return "vpn";
//    }
//    //    @GetMapping(path = "/getServers")
////    public @ResponseBody Iterable<Server> getServers(){
////        //model.addAttribute("servery", serverRepository.findAll());
////        return serverRepository.findAll();
////    }
//    @GetMapping(path = "/panel/router")
//    public String getRouters(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        boolean hasUserRole = authentication.getAuthorities().stream()
//                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
//        List<Router> routers = routerRepository.findAll();
//        List<Router> routersUser = new ArrayList<>();
//        for (int i = 0; i < routers.size(); i++) {
//            if (routers.get(i).getRole_id() == 2) routersUser.add(routers.get(i));
//        }
//
//        if (hasUserRole == true) model.addAttribute("routers", routers);
//        else model.addAttribute("routers", routersUser);
//        model.addAttribute("auth", hasUserRole);
//        return "router";
//    }
//    @GetMapping(path = "/panel/rdp")
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
//        if(hasUserRole==true) model.addAttribute("rdps",rdps);
//        else model.addAttribute("rdps", rdpUser);
//        model.addAttribute("auth", hasUserRole);
//        return "rdp";
//    }
////    @GetMapping(path = "/getServers")
////    public @ResponseBody Iterable<Server> getServers(){
////        //model.addAttribute("servery", serverRepository.findAll());
////        return serverRepository.findAll();
////    }
//
//    @GetMapping(path = "/panel/database")
//    public String getDataBase(Model model){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        boolean hasUserRole = authentication.getAuthorities().stream()
//                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
//        List<Data_base> dataBases = databaseRepository.findAll();
//        List<Data_base> dataBaseUser = new ArrayList<>();
//        for(int i=0;i<dataBases.size();i++){
//            if(dataBases.get(i).getRole_id()==2) dataBaseUser.add(dataBases.get(i));
//        }
//
//        if(hasUserRole==true) model.addAttribute("dataBases",dataBases);
//        else model.addAttribute("dataBases", dataBaseUser);
//        model.addAttribute("auth", hasUserRole);
//        return "database";
//    }
//
//    @GetMapping(path = "/panel/server")
//    public String getServers(Model model){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        boolean hasUserRole = authentication.getAuthorities().stream()
//                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
//        List<Server> servers = serverRepository.findAll();
//        List<Server> serverUser = new ArrayList<>();
//        for(int i=0;i<servers.size();i++){
//            if(servers.get(i).getRole_id()==2) serverUser.add(servers.get(i));
//        }
//        if(hasUserRole==true) model.addAttribute("servery",servers);
//        else model.addAttribute("servery", serverUser);
//        model.addAttribute("auth", hasUserRole);
//        return "serwer";
//    }
////    @GetMapping(path = "/getServers")
////    public @ResponseBody Iterable<Server> getServers(){
////        //model.addAttribute("servery", serverRepository.findAll());
////        return serverRepository.findAll();
////    }
//    @GetMapping(path = "/admin/ftpdata")
//    public String getFtpData(Model model){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        boolean hasUserRole = authentication.getAuthorities().stream()
//                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
//        List<Ftp_data> ftpData = ftp_dataRepository.findAll();
//        model.addAttribute("ftp_data",ftpData);
//        model.addAttribute("auth", hasUserRole);
//        return "ftpdata";
//    }
//
//}
