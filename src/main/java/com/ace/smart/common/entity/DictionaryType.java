package com.ace.smart.common.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-06-29
 */
public class DictionaryType {
    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}