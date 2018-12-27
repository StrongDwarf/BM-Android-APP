package com.app.bm.bm.common;

public class ApiUrl{
    /**
     * 登录相关url
     */
    private String localhost;           //主机地址
    private String loginByPasswd;       //手机密码登录
    private String loginForgetPwd;      //忘记密码

    /**
     * 个人中心相关url
     */
    private String userInfo;            //查询用户个人信息


    public ApiUrl(){
        this.localhost = "http://mdev.bmtrip.com/api/v3/";
        this.loginByPasswd = this.localhost + "c=login&a=loginByPasswd";
    }

    public ApiUrl(String localhost){
        this.localhost = localhost;

    }
}