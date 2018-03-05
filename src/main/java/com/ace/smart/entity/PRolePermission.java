package com.ace.smart.entity;

import java.util.List;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2017-11-04
 */
public class PRolePermission {

    private Long rid;
    private Long pid;
    private List<PPermission> uPermissions;
    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public List<PPermission> getuPermissions() {
        return uPermissions;
    }

    public void setuPermissions(List<PPermission> uPermissions) {
        this.uPermissions = uPermissions;
    }
}