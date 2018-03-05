package com.ace.smart.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtil {
    private static final Logger logger = LoggerFactory.getLogger(CollectionUtil.class);

    public static boolean listIsNull(List<?> list){
        return list.size()>0&&list!=null;
    }

    public static boolean listIsExit(List<?> list){
        return list.size()>=1&&list!=null?false:true;
    }

    public static List<Long> strToList(String[] id){
        List<Long> longList = new ArrayList<Long>();
        for (String idStr:id) {
            longList.add(Long.parseLong(idStr));
        }
        return longList;
    }
}
