package com.ace.smart.common.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SolrBeanUtil {

    /**
     * 将SolrDocument转换成Bean
     * @param record
     * @param clazz
     * @return
     */
    public static Object toBean(SolrDocument record, Class clazz){
        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields){
            Object value = record.get(field.getName());
            try {
                BeanUtils.setProperty(obj, field.getName(), value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    /**
     * 将SolrDocumentList转换成BeanList
     * @param records
     * @param clazz
     * @return
     */
    public static Object toBeanList(SolrDocumentList records, Class clazz){
        List list = new ArrayList();
        for(SolrDocument record : records){
            list.add(toBean(record,clazz));
        }
        return list;
    }
}
