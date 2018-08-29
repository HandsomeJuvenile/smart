package com.ace.smart.common.entity;

import javax.validation.constraints.NotNull;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2017-11-04
 */
public class UPermission {
    @NotNull
    private Long id;
    private String url;
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}