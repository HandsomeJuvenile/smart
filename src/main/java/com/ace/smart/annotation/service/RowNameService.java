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

    public static void load(Class<?> clz,List<?> list) throws Exception {
        Object object = clz.newInstance();
        Object str ;
        Annotation[] annotations = clz.getAnnotations();

        // 获得到类的属性注解
        //Field[ ] fields = clz.getDeclaredFields();
        List<String> titles = new ArrayList();

        for (Object obj: list) {
            Field[ ] fields =  obj.getClass().getDeclaredFields();
            for (Field field:fields) {
                Field f = clz.getDeclaredField(field.getName());
                RowName afd = f.getAnnotation(RowName.class);
                if(afd !=null){
                    PropertyDescriptor descriptor = new PropertyDescriptor(f.getName(),clz);
                    Method rM = descriptor.getReadMethod();
                    if(f.getType().equals(Integer.class)){
                        str = (Integer) rM.invoke(object);
                    }else {
                        str = (String) rM.invoke(object);
                    }
                    System.out.println(afd.name()+"           "+str);
                    titles.add(afd.name());
                }
            }
        }



    }





    public static void main(String[] args) {
        List<PUser> list = new ArrayList<PUser>();
        PUser pUser = new PUser();
        pUser.setEmail("1414790478@");
        pUser.setPhone("136546");
        pUser.setUserLoginName("zzh");
        pUser.setPswd("1111111");
        pUser.setStatus("0");
        pUser.setCreateTime("2017-08-20");
        pUser.setId(1654651465165466l);
        pUser.setSelfIntroduction("HELLO");
        pUser.setuAddress("wuhu");
        list.add(pUser);
        try {
            load(PUser.class,list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
