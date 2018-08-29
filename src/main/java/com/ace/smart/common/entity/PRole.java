package com.ace.smart.common.entity;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wcyong
 * @date 2018-01-29
 */
public class PRole {
    @NotNull
    private Long rId;
    @NotNull
    private String rName;
    private String rStatus;
    private String createTime;
    private String remark;
    private List<PPermission> pPermissions;

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName == null ? null : rName.trim();
    }

    public String getrStatus() {
        return rStatus;
    }

    public void setrStatus(String rStatus) {
        this.rStatus = rStatus == null ? null : rStatus.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public List<PPermission> getpPermissions() {
        return pPermissions;
    }

    public void setpPermissions(List<PPermission> pPermissions) {
        this.pPermissions = pPermissions;
    }

    @Override
    public String toString() {
        return "PRole{" +
                "rId=" + rId +
                ", rName='" + rName + '\'' +
                ", rStatus='" + rStatus + '\'' +
                ", createTime='" + createTime + '\'' +
                ", remark='" + remark + '\'' +
                ", pPermissions=" + pPermissions +
                '}';


    }
}