package com.ace.smart.common.entity;

import lombok.Data;

@Data
public class Email {
    private String token;
    private String recive;
    private String []recives;
    private String content;
    private String filePath;
    private String imgPath;
    private String subject;

}
