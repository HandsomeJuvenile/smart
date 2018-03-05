package com.ace.smart.container;

import java.util.HashMap;
import java.util.Map;

public class EmailContainer {

    private  Map<String,String> emailMap = new HashMap<String,String>();

    private static EmailContainer instance = null;
    private EmailContainer(){}
    public static EmailContainer getInstance() {
        if (instance == null) {
            synchronized (EmailContainer.class) {
                if (instance == null) {
                    instance = new EmailContainer();
                }
            }
        }
        return instance;
    }

    public Map<String, String> getEmailMap() {
        return emailMap;
    }

    public void setEmailMap(Map<String, String> emailMap) {
        this.emailMap = emailMap;
    }
}
