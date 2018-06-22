package com.ace.smart.annotation.service;

import com.ace.smart.annotation.RowName;
import com.ace.smart.entity.PUser;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RowNameService {

    private Method rM;

    /**
     * 解析注解@RowName 为excel第一行设立值
     * @param clz
     * @return
     * @throws Exception
     */
    public static List<String> load(Class<?> clz) throws Exception {
        Object object = clz.newInstance();
        Object str ;
        Annotation[] annotations = clz.getAnnotations();
        // 获得到类的属性注解
        Field[ ] fields = clz.getDeclaredFields();
        List<String> titles = new ArrayList<String>();
        for (Field field:fields) {
            Field f = clz.getDeclaredField(field.getName());
            RowName afd = f.getAnnotation(RowName.class);
            if(afd !=null){
                titles.add(afd.name());
            }
        }
        return titles;
    }

}
