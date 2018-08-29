package com.ace.smart.common.entity;

import lombok.Data;

import java.util.List;

@Data
public class PRolePermission {

    private Long rid;
    private Long pid;
    private List<PPermission> uPermissions;
}