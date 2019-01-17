package com.app.bm.bm.common.datas;

public class LoginForgetPwdSendData{
    private String phone;
    private String code;
    private String pwd;

    public LoginForgetPwdSendData(String phone,String code,String pwd){
        this.phone = phone;
        this.code = code;
        this.pwd = pwd;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getCode() {
        return code;
    }

    public String getPhone() {
        return phone;
    }

    public String getPwd() {
        return pwd;
    }
}