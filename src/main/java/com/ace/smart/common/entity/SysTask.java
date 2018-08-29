package com.ace.smart.common.entity;

import lombok.Data;

import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-06-08
 */
@Data
public class SysTask {
    private Long id;
    private String cronExpression;
    private String methodName;
    private String description;// 描述
    private Date updateBy;// 更新者
    private String beanClass; //任务执行时调用哪个类的方法 包名+类名
    private String  createDate; //创建日期
    private String jobStatus; //任务状态
    private String jobGroup;// 任务分组
    private String updateDate;
    private String createBy;// 创建者
    private String springBean;
    private String jobName;// 任务名
}