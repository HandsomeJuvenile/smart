package com.ace.smart.common.entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TsMenuinfo implements Serializable{
    @NotNull
    private String menuId;

    private String subsysCode;

    private String menuName;

    private String upMenuId;

    private String menuClass;

    private String menuType;

    @NotNull
    private String moduleId;

    private String inParamCode1;

    private String inParamValue1;

    private String inParamCode2;

    private String inParamValue2;

    private String inParamCode3;

    private String inParamValue3;

    private String removeTag;

    private String rsrvStr1;

    private String rsrvStr2;

    private String rsrvStr3;

    private String rsrvStr4;

    private String rsrvStr5;

    private String rsrvStr6;

    private String rsrvStr7;

    private String rsrvStr8;

    private String rsrvStr9;

    private Integer rsrvStr0;

    private String inStaffId;

    private Date inDate;

    private String remark;

    private TsModuleinfo tsModuleinfo;

    private List<TsMenuinfo> tsMenuinfoList;

    public String getMenuId() {
        return menuId;
    }

    public TsMenuinfo() {
        this.tsMenuinfoList = new ArrayList<TsMenuinfo>();
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    public String getSubsysCode() {
        return subsysCode;
    }

    public void setSubsysCode(String subsysCode) {
        this.subsysCode = subsysCode == null ? null : subsysCode.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getUpMenuId() {
        return upMenuId;
    }

    public void setUpMenuId(String upMenuId) {
        this.upMenuId = upMenuId == null ? null : upMenuId.trim();
    }

    public String getMenuClass() {
        return menuClass;
    }

    public void setMenuClass(String menuClass) {
        this.menuClass = menuClass == null ? null : menuClass.trim();
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType == null ? null : menuType.trim();
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId == null ? null : moduleId.trim();
    }

    public String getInParamCode1() {
        return inParamCode1;
    }

    public void setInParamCode1(String inParamCode1) {
        this.inParamCode1 = inParamCode1 == null ? null : inParamCode1.trim();
    }

    public String getInParamValue1() {
        return inParamValue1;
    }

    public void setInParamValue1(String inParamValue1) {
        this.inParamValue1 = inParamValue1 == null ? null : inParamValue1.trim();
    }

    public String getInParamCode2() {
        return inParamCode2;
    }

    public void setInParamCode2(String inParamCode2) {
        this.inParamCode2 = inParamCode2 == null ? null : inParamCode2.trim();
    }

    public String getInParamValue2() {
        return inParamValue2;
    }

    public void setInParamValue2(String inParamValue2) {
        this.inParamValue2 = inParamValue2 == null ? null : inParamValue2.trim();
    }

    public String getInParamCode3() {
        return inParamCode3;
    }

    public void setInParamCode3(String inParamCode3) {
        this.inParamCode3 = inParamCode3 == null ? null : inParamCode3.trim();
    }

    public String getInParamValue3() {
        return inParamValue3;
    }

    public void setInParamValue3(String inParamValue3) {
        this.inParamValue3 = inParamValue3 == null ? null : inParamValue3.trim();
    }

    public String getRemoveTag() {
        return removeTag;
    }

    public void setRemoveTag(String removeTag) {
        this.removeTag = removeTag == null ? null : removeTag.trim();
    }

    public String getRsrvStr1() {
        return rsrvStr1;
    }

    public void setRsrvStr1(String rsrvStr1) {
        this.rsrvStr1 = rsrvStr1 == null ? null : rsrvStr1.trim();
    }

    public String getRsrvStr2() {
        return rsrvStr2;
    }

    public void setRsrvStr2(String rsrvStr2) {
        this.rsrvStr2 = rsrvStr2 == null ? null : rsrvStr2.trim();
    }

    public String getRsrvStr3() {
        return rsrvStr3;
    }

    public void setRsrvStr3(String rsrvStr3) {
        this.rsrvStr3 = rsrvStr3 == null ? null : rsrvStr3.trim();
    }

    public String getRsrvStr4() {
        return rsrvStr4;
    }

    public void setRsrvStr4(String rsrvStr4) {
        this.rsrvStr4 = rsrvStr4 == null ? null : rsrvStr4.trim();
    }

    public String getRsrvStr5() {
        return rsrvStr5;
    }

    public void setRsrvStr5(String rsrvStr5) {
        this.rsrvStr5 = rsrvStr5 == null ? null : rsrvStr5.trim();
    }

    public String getRsrvStr6() {
        return rsrvStr6;
    }

    public void setRsrvStr6(String rsrvStr6) {
        this.rsrvStr6 = rsrvStr6 == null ? null : rsrvStr6.trim();
    }

    public String getRsrvStr7() {
        return rsrvStr7;
    }

    public void setRsrvStr7(String rsrvStr7) {
        this.rsrvStr7 = rsrvStr7 == null ? null : rsrvStr7.trim();
    }

    public String getRsrvStr8() {
        return rsrvStr8;
    }

    public void setRsrvStr8(String rsrvStr8) {
        this.rsrvStr8 = rsrvStr8 == null ? null : rsrvStr8.trim();
    }

    public String getRsrvStr9() {
        return rsrvStr9;
    }

    public void setRsrvStr9(String rsrvStr9) {
        this.rsrvStr9 = rsrvStr9 == null ? null : rsrvStr9.trim();
    }

    public Integer getRsrvStr0() {
        return rsrvStr0;
    }

    public void setRsrvStr0(Integer rsrvStr0) {
        this.rsrvStr0 = rsrvStr0;
    }

    public String getInStaffId() {
        return inStaffId;
    }

    public void setInStaffId(String inStaffId) {
        this.inStaffId = inStaffId == null ? null : inStaffId.trim();
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public TsModuleinfo getTsModuleinfo() {
        return tsModuleinfo;
    }

    public void setTsModuleinfo(TsModuleinfo tsModuleinfo) {
        this.tsModuleinfo = tsModuleinfo;
    }

    public List<TsMenuinfo> getTsMenuinfoList() {
        return tsMenuinfoList;
    }

    public void setTsMenuinfoList(List<TsMenuinfo> tsMenuinfoList) {
        this.tsMenuinfoList = tsMenuinfoList;
    }
}