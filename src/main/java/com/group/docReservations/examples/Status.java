package com.group.docReservations.examples;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Status {
    int id;
    String nazwa;
    String url;
    public Status(int id, String nazwa, String url){
        this.id=id;
        this.nazwa=nazwa;
        this.url=url;
    }
    public static String getStatus(String url) throws IOException {
        String result = "";
        int code = 200;
        try {
            URL siteURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.connect();
            code = connection.getResponseCode();
            if (code == 200) {
                result = "ON";
            }
            else {
                result = "OFF";
            }
        } catch (Exception e) {
            if(e.getMessage()=="Connect timed out") result = "OFF";
            else result = "ERROR "+ e.getMessage();
        }
        //System.out.println(url + "\t\tStatus:" + result);
        return result;
    }


    // restart huawei EMUI.RebootController.rebootExe()


}

