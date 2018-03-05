package com.ace.smart.entity;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2017-12-27
 */
public class PPermission {
    @NotNull
    private Long menuId;
    private String url;
    private String name;
    private String parentId;
    private String createTime;
    private String updateTime;
    private String status;
    private boolean checked;

    private List<PPermission> children;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<PPermission> getChildren() {
        return children;
    }

    public void setChildren(List<PPermission> children) {
        this.children = children;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}