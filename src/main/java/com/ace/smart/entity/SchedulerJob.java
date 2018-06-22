package com.ace.smart.entity;

import lombok.Data;

import java.io.Serializable;

public class SchedulerJob implements Serializable{

    public static final String STATUS_RUNNING = "1";
    public static final String STATUS_NOT_RUNNING = "0";
    public static final String CONCURRENT_IS = "1";
    public static final String CONCURRENT_NOT = "0";
}
