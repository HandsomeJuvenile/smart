package com.ace.smart.common.util;

import java.util.HashMap;
import java.util.Map;

public class LayuiMap {

    public static Map<String,Object> retrunMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("msg","hello");
        map.put("code", 0);

        return map;
    }
}
