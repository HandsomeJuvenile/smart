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
@SuppressWarnings("all")
public class UUser {
    @NotNull
    private Long id;
    private String nickname;
    private String email;
    @NotNull
    private String pswd;
    private String createTime;
    private String lastLoginTime;
    private Long status;
    private List<URole> roles;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd == null ? null : pswd.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public List<URole> getRoles() {
        return roles;
    }

    public void setRoles(List<URole> roles) {
        this.roles = roles;
    }
}