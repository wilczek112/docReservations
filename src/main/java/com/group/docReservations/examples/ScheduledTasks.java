//package com.group.docReservations;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import com.group.docReservations.classes.*;
//import com.group.docReservations.repository.*;
//import com.wilk.group.Project_web_admin.classes.*;
//import com.wilk.group.Project_web_admin.repository.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ScheduledTasks {
//    @Autowired
//    ServerRepository serverRepository;
//    @Autowired
//    RouterRepository routerRepository;
//    @Autowired
//    VpnRepository vpnRepository;
//    @Autowired
//    RdpRepository rdpRepository;
//    @Autowired
//    Ftp_dataRepository ftp_dataRepository;
//    @Autowired
//    DatabaseRepository databaseRepository;
//
//    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
//
//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//
//    @Async
//    //@Scheduled(fixedDelay = 60000)
//    public void reportCurrentTime() throws InterruptedException {
//        log.info("The time is now {}", dateFormat.format(new Date()));
//    }
//    @Async
//    @Scheduled(fixedDelay = 60000)
//    public void reportStatusServers() throws InterruptedException, IOException {
//        List<Server> serverList = serverRepository.findAll();
//        for (Server server : serverList) {
//            server.setStatus(Status.getStatus("http://"+server.getIp_address()));
//            serverRepository.save(server);
//        }
//        log.info("Status updated - server - {}", dateFormat.format(new Date()));
//    }
//    @Async
//    @Scheduled(fixedDelay = 60000)
//    public void reportStatusRouters() throws InterruptedException, IOException {
//        List<Router> routerList = routerRepository.findAll();
//        for (Router router : routerList) {
//            router.setStatus(Status.getStatus("http://"+router.getIp_address()));
//            routerRepository.save(router);
//        }
//        log.info("Status updated - router - {}", dateFormat.format(new Date()));
//    }
//    @Async
//    @Scheduled(fixedDelay = 60000)
//    public void reportStatusVpns() throws InterruptedException, IOException {
//        List<Vpn> vpnList = vpnRepository.findAll();
//        for (Vpn vpn : vpnList) {
//            vpn.setStatus(Status.getStatus("http://"+vpn.getIp_address()));
//            vpnRepository.save(vpn);
//        }
//        log.info("Status updated - vpn - {}", dateFormat.format(new Date()));
//    }
//    @Async
//    @Scheduled(fixedDelay = 60000)
//    public void reportStatusRdps() throws InterruptedException, IOException {
//        List<Rdp> rdpList = rdpRepository.findAll();
//        for (Rdp rdp : rdpList) {
//            rdp.setStatus(Status.getStatus("http://"+rdp.getIp_address()));
//            rdpRepository.save(rdp);
//        }
//        log.info("Status updated - rdp - {}", dateFormat.format(new Date()));
//    }
//    @Async
//    @Scheduled(fixedDelay = 60000)
//    public void reportStatusFtps() throws InterruptedException, IOException {
//        List<Ftp_data> ftp_dataList = ftp_dataRepository.findAll();
//        for (Ftp_data ftp_data : ftp_dataList) {
//            ftp_data.setStatus(Status.getStatus("http://"+ftp_data.getIp_address()));
//            ftp_dataRepository.save(ftp_data);
//        }
//        log.info("Status updated - ftp - {}", dateFormat.format(new Date()));
//    }
//    @Async
//    @Scheduled(fixedDelay = 60000)
//    public void reportStatusDatabases() throws InterruptedException, IOException {
//        List<Data_base> data_baseList = databaseRepository.findAll();
//        for (Data_base data_base : data_baseList) {
//            data_base.setStatus(Status.getStatus("http://"+data_base.getIp_address()));
//            databaseRepository.save(data_base);
//        }
//        log.info("Status updated - database - {}", dateFormat.format(new Date()));
//    }
//}
//
