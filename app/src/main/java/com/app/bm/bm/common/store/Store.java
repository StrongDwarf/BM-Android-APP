package com.app.bm.bm.common.store;


import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;

/**
 * 单例:Store 存储用户经常需要使用到的数据
 */
public class Store{
    private Activity context;
    private static Store store;     //存储实例
    private Boolean loginState;     //登录状态
    private String token;           //用户token
    private String userName;        //用户姓名
    private String userSignature;   //用户个性签名
    private Bitmap headImg;         //用户头像

    public Store(Activity context){
        this.context = context;
        update();
    }

    public void update(){
        this.loginState = SPStorage.getInstance(context).getBoolean(StoreCode.getInstance().getLoginState());
        if(this.loginState){
            this.token = SPStorage.getInstance(context).getString(StoreCode.getInstance().getToken());
        }else{

        }
    }

    public void setLoginState(Boolean loginState) {
        this.loginState = loginState;
    }

    public void setHeadImg(Bitmap headImg) {
        this.headImg = headImg;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature;
    }

    public Boolean getLoginState() {
        return loginState;
    }

    public String getUserName() {
        return userName;
    }

    public String getToken() {
        return token;
    }

    public Bitmap getHeadImg() {
        return headImg;
    }

    public static Store getStore() {
        return store;
    }

    public String getUserSignature() {
        return userSignature;
    }

    //判断用户是否登录
    private Boolean isLogin(){
        return SPStorage.getInstance(context).getBoolean(StoreCode.getInstance().getLoginState());
    }

    public static synchronized Store getInstance(Activity context) {
        if(store == null){
            store = new Store(context);
        }
        return store;
    }
}