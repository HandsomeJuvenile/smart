package com.ace.smart.entity;

import java.util.List;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2017-11-04
 */
public class UUserRole {
    private Long uid;
    private Long rid;
    private List<URole> uRoles;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public List<URole> getuRoles() {
        return uRoles;
    }

    public void setuRoles(List<URole> uRoles) {
        this.uRoles = uRoles;
    }
}