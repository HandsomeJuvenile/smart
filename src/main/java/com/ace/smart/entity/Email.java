package com.ace.smart.entity;


public class Email {
    private String token;
    private String recive;
    private String []recives;
    private String content;
    private String filePath;
    private String imgPath;
    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getRecive() {
        return recive;
    }

    public void setRecive(String recive) {
        this.recive = recive;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getRecives() {
        return recives;
    }

    public void setRecives(String []recives) {
        this.recives = recives;
    }
}
