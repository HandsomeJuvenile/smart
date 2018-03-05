package com.ace.smart.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ManHuaApi implements YiYuanApi{
    private static final String URLL = "http://route.showapi.com/978-1?&id=/xe/7007558.shtml&showapi_appid=52687&count=5&showapi_sign=90481383dd904aa2b7a85c4c38b2c5a8";

    @Override
    public String getContent() {
        java.net.URL u = null;
        byte b[] = null;
        try {
            u = new URL(URLL);
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
                return encourage;
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

    public static void main(String[] args) {
        YiYuanApi yiYuanApi = new ManHuaApi();
        System.out.println(yiYuanApi.getContent());

    }
}
