package com.ace.smart.json.entity;

public class ShowApiBody {
    private String ret_code;
    private String ret_mssage;
    private String data;
    private Content content;
    public String getRet_code() {
        return ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    public String getRet_mssage() {
        return ret_mssage;
    }

    public void setRet_mssage(String ret_mssage) {
        this.ret_mssage = ret_mssage;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
