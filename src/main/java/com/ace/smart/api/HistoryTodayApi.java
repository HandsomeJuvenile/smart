package com.ace.smart.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class HistoryTodayApi extends YiYuanApi{
    private static final String s = "http://route.showapi.com/119-42?showapi_appid=52687&count=5&showapi_sign=90481383dd904aa2b7a85c4c38b2c5a8";

    @Override
    public  String getContent(String ur) {
       return super.getContent(s);
    }

    public static void main(String[] args) {
        HistoryTodayApi historyTodayApi = new HistoryTodayApi();
        System.out.println(historyTodayApi.getContent(s));
    }
}
