package com.app.bm.bm.common;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * 一个登陆单例,所有的登录状态操作都使用该单例进行
 */
public class Login{
    private String token;
    private Context context;

    private Login(Activity context){
        this.context = context;
        token = getToken();
    }

    //设置登录状态
    public void setToken(String token){
        //保存token
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences("bm",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("usertoken",token);
        edit.commit();
        this.token = token;
    }

    public String getToken(){
        //获取token
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences("bm",Context.MODE_PRIVATE);
        return sp.getString("usertoken","");
    }

    //外部获取单例的接口
   // public static Login getInstance(){
    //    return loginInstance;
    //}
}