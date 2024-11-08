package com.group.docReservations.examples.controller;//package com.group.docReservations.controller;
//
//import com.group.docReservations.repository.*;
//import com.wilk.group.Project_web_admin.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//class DatabaseCleanController{
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    ServerRepository serverRepository;
//    @Autowired
//    RoleRepository roleRepository;
//    @Autowired
//    RouterRepository routerRepository;
//    @Autowired
//    RdpRepository rdpRepository;
//    @Autowired
//    Ftp_dataRepository ftpRepository;
//    @Autowired
//    DatabaseRepository databaseRepository;
//
//    @RequestMapping(path="/destroy")
//    public String destroy(){
//        userRepository.deleteAll();
//        serverRepository.deleteAll();
//        roleRepository.deleteAll();
//        routerRepository.deleteAll();
//        rdpRepository.deleteAll();
//        ftpRepository.deleteAll();
//        databaseRepository.deleteAll();
//        return "redirect:/";
//    }
//}