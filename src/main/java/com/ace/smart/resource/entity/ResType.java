package com.ace.smart.resource.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-06-26
 */
public class ResType {
    private Long resId;

    private String resName;

    public Long getResId() {
        return resId;
    }

    public void setResId(Long resId) {
        this.resId = resId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }

}