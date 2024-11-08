package com.group.docReservations;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FtpServiceTest {

    @Autowired
    private FtpService ftpService;

    @Test
    public void test() throws Exception {
//        RouterPanel.getStatus("http://192.168.0.1");
        long currentTimeMillis = System.currentTimeMillis();
        //String directoryPath = String.format("/G/create-directory-test-%d", currentTimeMillis);
//        String path = String.format("/G/test-upload-file-%d.jpg", currentTimeMillis);
//        String newPath = String.format("/G/test-rename-file-%d.jpg", currentTimeMillis);
        ftpService.ftpSetAll("192.168.0.1", 21, "admin", "Testlog123");
        FTPClient ftpClient = ftpService.loginFtp();
        ftpService.printTreeDir("/G/", ftpClient);
        ftpService.printTreeFiles("/G/", ftpClient);
//        ftpService.createDirectory(directoryPath, ftpClient);
//        ftpService.uploadFile("C:\\test.jpg", path, ftpClient);
//        ftpService.renameFile(path, newPath, ftpClient);
//        byte[] downloadFile = ftpService.downloadFile(newPath, ftpClient);
//        //System.out.println("Downloaded file : " + new String(downloadFile));
//        ftpService.deleteFile(newPath, ftpClient);
//        ftpService.deleteDirectory(directoryPath, ftpClient);
        ftpClient.disconnect();
    }
}