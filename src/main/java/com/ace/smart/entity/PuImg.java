package com.ace.smart.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2017-12-07
 */
public class PuImg {
    private String imgId;

    @NotNull
    private String userId;

    /**
     * 0:用户头像  
     */
    private String type;

    private String jpgurl;

    private String uploadTime;

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId == null ? null : imgId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getJpgurl() {
        return jpgurl;
    }

    public void setJpgurl(String jpgurl) {
        this.jpgurl = jpgurl == null ? null : jpgurl.trim();
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }
}