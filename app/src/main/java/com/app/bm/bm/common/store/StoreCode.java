package com.app.bm.bm.common.store;

/**
 * 单例:保存应用程序中相关属性存储在Sharedreferences中时使用的名称
 */
public class StoreCode{
    private static StoreCode storeCode;

    private String loginState;
    private String userName;
    private String userSex;
    private String token;

    private StoreCode(){
        this.loginState = "login_state";
        this.userName = "user_name";
        this.userSex = "user_Sex";
        this.token = "token";
    }

    public String getLoginState() {
        return loginState;
    }

    public String getToken() {
        return token;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public static synchronized StoreCode getInstance() {
        if(storeCode == null){
            storeCode = new StoreCode();
        }
        return storeCode;
    }
}