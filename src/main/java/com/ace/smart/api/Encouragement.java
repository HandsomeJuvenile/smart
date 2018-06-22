package com.ace.smart.api;


import com.ace.smart.json.Json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Encouragement extends YiYuanApi{

    private static final String URL = "http://route.showapi.com/1211-1?showapi_appid=52687&count=5&showapi_sign=90481383dd904aa2b7a85c4c38b2c5a8";

    public  String getEncouragement(){
        return super.getContent(URL);
    }

    private String getUrl(String type){
        StringBuilder sb = new StringBuilder("http://route.showapi.com/");
        sb.append(type);
        sb.append("?showapi_appid=52687&count=5&showapi_sign=90481383dd904aa2b7a85c4c38b2c5a8");
        return sb.toString();
    }

    public static void main(String[] args) {
        Encouragement encouragement = new Encouragement();
        System.out.println(encouragement.getEncouragement());
    }
}
