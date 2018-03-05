package com.ace.smart.page;

import com.github.pagehelper.PageInfo;

/*
用于分页控制页数
 */
public class LeavePage {
    private int total = 0;
    private int pageSize = 0;
    private int currentPage = 0;
    private String jumpUrl = "";

    public LeavePage() {
    }

    public LeavePage(int total, int pageSize, int currentPage, String jumpUrl) {
        this.total = total;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.jumpUrl = jumpUrl;
    }

    public static String setLeavePage (PageInfo pageInfo){
        boolean isFirstPage = pageInfo.isIsFirstPage();
        boolean isLastPage = pageInfo.isIsLastPage();
        int currentPage = pageInfo.getPageNum();
        long totalPage = pageInfo.getLastPage();
        StringBuilder stringBuilder = new StringBuilder(32);
        stringBuilder.append("<li ");
        if(isFirstPage || pageInfo.getTotal()<=1) {
            stringBuilder.append("class=\"disabled\"");
        }
        stringBuilder.append(">");
        stringBuilder.append("<a href=\"#\">首页</a></li>");
        stringBuilder.append("<li ");
        if(isFirstPage){
            stringBuilder.append("class=\"disabled\"");
        }
        stringBuilder.append(">");
        stringBuilder.append("<a href=\"#\">上一页</a></li>");
        stringBuilder.append("<li><a href=\"#\">");
        stringBuilder.append(currentPage+"");
        stringBuilder.append("</a></li>");
        stringBuilder.append("<li ");
        if(isLastPage || pageInfo.getTotal()<=1){
            stringBuilder.append("class=\"disabled\"");
        }
        stringBuilder.append(">");
        stringBuilder.append("<a href=\"#\">尾页</a></li>");
        stringBuilder.append("<li ");
        if (isLastPage){
            stringBuilder.append("class=\"disabled\"");
        }
        stringBuilder.append(">");
        stringBuilder.append("<a href=\"#\">下一页</a></li>");
        return stringBuilder.toString();
    }
}
