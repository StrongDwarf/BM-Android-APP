package com.app.bm.bm.common.apiUrls;

public class ApiUrl{
    //主机地址
    private static String hostUrl = "http://mdev.bmtrip.com";

    //get方法
    //手机号状态查询地址
    private static String loginStatus = hostUrl + "/api/v3/login/status";
    //首页数据
    private static String homePage = hostUrl + "/api/v3/m1/homepage";

    //post方法
    //忘记密码
    private static String loginForgetPwd = hostUrl + "/api/v3/login/forget/pwd";
    //获取注册短信验证码
    private static String registerCode = hostUrl + "/api/v3/login/code";

    public static String getHostUrl() {
        return hostUrl;
    }

    public static String getLoginStatus() {
        return loginStatus;
    }

    public static String getLoginForgetPwd() {
        return loginForgetPwd;
    }

    public static String getRegisterCode() {
        return registerCode;
    }

    public static String getHomePage() {
        return homePage;
    }
}