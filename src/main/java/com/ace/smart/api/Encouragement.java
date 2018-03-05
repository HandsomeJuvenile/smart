package com.ace.smart.api;


import com.ace.smart.json.Json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Encouragement {

    private static final String URL = "http://route.showapi.com/1211-1?showapi_appid=52687&count=5&showapi_sign=90481383dd904aa2b7a85c4c38b2c5a8";

    public static String getEncouragement(){
        java.net.URL u = null;
        byte b[] = null;
        try {
            u = new URL(URL);
            InputStream in=u.openStream();
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            try {
                byte buf[]=new byte[1024];
                int read = 0;
                while ((read = in.read(buf)) > 0) {
                    out.write(buf, 0, read);
                }
                 b =out.toByteArray( );
                String encourage = new String (b,"utf-8");
                return Json.getStr(encourage);
            }  finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getUrl(String type){
        StringBuilder sb = new StringBuilder("http://route.showapi.com/");
        sb.append(type);
        sb.append("?showapi_appid=52687&count=5&showapi_sign=90481383dd904aa2b7a85c4c38b2c5a8");
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getEncouragement());
    }
}
