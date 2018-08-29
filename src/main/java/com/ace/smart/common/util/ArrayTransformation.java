package com.ace.smart.common.util;

public class ArrayTransformation {

    public static Long[] strToLong(String [] strings){
        Long[] longs = new Long[strings.length];
        for (int i=0;i<strings.length;i++) {
            longs[i] = Long.parseLong(strings[i]);
        }
        return longs;
    }
}
