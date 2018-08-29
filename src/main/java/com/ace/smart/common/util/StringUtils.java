package com.ace.smart.common.util;

public class StringUtils {

    public static boolean allIsNotNull(String... s){
        if(s == null || s.length < 1) {
            return false;
        }

        //System.out.println(s[0] == null);

        for (String s1:s) {
            if(s1 == null){
                return false;
            }
        }
        return true;
    }

    public static boolean allIsEmpty(String... s){
        if (allIsNotNull(s)){
            for (String s1:s) {
                if(s1.isEmpty()){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String [] strings = new String[4];
        strings[0] = "1";
        strings[1] = "";
        strings[2] = "1";
        //strings[3] = "1";
        System.out.println(allIsNotNull(null));
        System.out.println(allIsEmpty(null));
    }
}
