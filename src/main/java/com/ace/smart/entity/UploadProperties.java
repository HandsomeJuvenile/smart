package com.ace.smart.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties("upload")
public class UploadProperties {

    // 获取存储位置
    private Map<String ,String> location;

    // 单个文件大小
    private String maxFileSize;

    // 单次上传总文件大小
    private String maxRequestSize;

    public String getBasePath() {
        String location = "";
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")) {
            location = this.getLocation().get("windows");
        } else {
            location = this.getLocation().get("linux");
        }
        return location;
    }
}
