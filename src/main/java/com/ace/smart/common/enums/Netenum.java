package com.ace.smart.common.enums;

public enum Netenum {
    online("1"){public String getName(){return "在线";}},
    offline("0"){public String getName(){return "离线";}};

    String value;

    Netenum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public abstract String getName();

    public static String getNetState(String netState){
        if(netState!=null){
            for (Netenum netenum:Netenum.values()) {
                if(netState.equals(netenum.getValue()))
                return netenum.getName();
            }

        }
        return null;
    }

}
