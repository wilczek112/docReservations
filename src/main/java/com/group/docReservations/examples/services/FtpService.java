package com.group.docReservations.examples.services;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

@Getter
@Setter
@Service
public class FtpService {
    private String host;
    private int port;
    private String username;
    private String password;

    public FTPClient loginFtp() throws Exception {
        String host = this.host;
        int port = this.port;
        String username = this.username;
        String password = this.password;
        FTPClient ftpClient = new FTPClient();
        ftpClient.addProtocolCommandListener(new ProtocolCommandListener() {
            @Override
            public void protocolCommandSent(ProtocolCommandEvent protocolCommandEvent) {
                System.out.printf("[%s][%d] Command sent : [%s]-%s", Thread.currentThread().getName(),
                        System.currentTimeMillis(), protocolCommandEvent.getCommand(),
                        protocolCommandEvent.getMessage());
            }

            @Override
            public void protocolReplyReceived(ProtocolCommandEvent protocolCommandEvent) {
                System.out.printf("[%s][%d] Reply received : %s", Thread.currentThread().getName(),
                        System.currentTimeMillis(), protocolCommandEvent.getMessage());
            }
        });
        ftpClient.connect(host, port);
        ftpClient.login(username, password);
        return ftpClient;
    }
    public void printTreeDir(String path, FTPClient ftpClient) throws Exception {
        for (FTPFile ftpFile : ftpClient.listFiles(path)) {
            System.out.println();
            if (ftpFile.isDirectory()) {
                System.out.printf("[printTree][%d] Get name : %s \n", System.currentTimeMillis(), ftpFile.getName());
            }
        }
    }
    public void printTreeFiles(String path, FTPClient ftpClient) throws Exception {
        for (FTPFile ftpFile : ftpClient.listFiles(path)) {
            System.out.println();
            if (ftpFile.isFile()) {
                System.out.printf("[printTree][%d] Get name : %s \n", System.currentTimeMillis(), ftpFile.getName());
            }
        }
    }
    public void createDirectory(String path, FTPClient ftpClient) throws Exception {
        System.out.println();
        System.out.printf("[createDirectory][%d] Is success to create directory : %s -> %b",
                System.currentTimeMillis(), path, ftpClient.makeDirectory(path));
        System.out.println();
    }
    public void uploadFile(String localPath, String remotePath, FTPClient ftpClient) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(localPath);
        System.out.println();
        System.out.printf("[uploadFile][%d] Is success to upload file : %s -> %b",
                System.currentTimeMillis(), remotePath, ftpClient.storeFile(remotePath, fileInputStream));
        System.out.println();
    }
    public void renameFile(String oldPath, String newPath, FTPClient ftpClient) throws Exception {
        System.out.println();
        System.out.printf("[renameFile][%d] Is success to rename file : from %s to %s -> %b",
                System.currentTimeMillis(), oldPath, newPath, ftpClient.rename(oldPath, newPath));
        System.out.println();
    }
    public byte[] downloadFile(String path, FTPClient ftpClient) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.out.println();
        System.out.printf("[downloadFile][%d] Is success to download file : %s -> %b",
                System.currentTimeMillis(), path, ftpClient.retrieveFile(path, byteArrayOutputStream));
        System.out.println();
        return byteArrayOutputStream.toByteArray();
    }
    public void deleteFile(String path, FTPClient ftpClient) throws Exception {
        System.out.println();
        System.out.printf("[deleteFile][%d] Is success to delete file : %s -> %b",
                System.currentTimeMillis(), path, ftpClient.deleteFile(path));
        System.out.println();
    }

    public void deleteDirectory(String path, FTPClient ftpClient) throws Exception {
        System.out.println();
        System.out.printf("[deleteDirectory][%d] Is success to delete directory : %s -> %b",
                System.currentTimeMillis(), path, ftpClient.removeDirectory(path));
        System.out.println();
    }
    public void ftpSetAll(String host, int port, String username, String password){
        this.host=host;
        this.port=port;
        this.username=username;
        this.password=password;
    }

}