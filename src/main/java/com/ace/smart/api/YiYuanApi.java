package com.ace.smart.api;

import com.ace.smart.json.Json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public abstract class YiYuanApi {

    public  String getContent(String url) {
        java.net.URL u = null;
        byte b[] = null;
        try {
            u = new URL(url);
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
}
