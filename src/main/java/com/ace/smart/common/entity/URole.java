package com.ace.smart.common.entity;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2017-11-04
 */
public class URole {
    @NotNull
    private Long id;
    private String name;
    private String type;
    private List<UPermission> uPermissions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public List<UPermission> getuPermissions() {
        return uPermissions;
    }

    public void setuPermissions(List<UPermission> uPermissions) {
        this.uPermissions = uPermissions;
    }
}