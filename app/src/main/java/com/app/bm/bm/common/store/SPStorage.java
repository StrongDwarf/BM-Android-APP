package com.app.bm.bm.common.store;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


import java.util.Set;

/**
 * SharedPreferences单例
 * 对SharedPreference函数的简单封装
 */
public class SPStorage{
    private Activity context;
    private static SPStorage sp;

    public SPStorage(Activity context){
        this.context = context;
    }

    public static synchronized SPStorage getInstance(Activity context) {
        if(sp == null){
            sp = new SPStorage(context);
        }
        return sp;
    }

    public void putString(String key,String value){
        SharedPreferences sp = context.getSharedPreferences("filename",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key,value);
        edit.commit();
    }

    public void putBoolean(String key,Boolean value){
        SharedPreferences sp = context.getSharedPreferences("filename",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key,value);
        edit.commit();
    }

    public void putInt(String key,int value){
        SharedPreferences sp = context.getSharedPreferences("filename",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key,value);
        edit.commit();
    }

    public void putSet(String key,Set<String> value){
        SharedPreferences sp = context.getSharedPreferences("filename",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putStringSet(key,value);
        edit.commit();
    }

    public String getString(String key){
        SharedPreferences sp = context.getSharedPreferences("filename", Context.MODE_PRIVATE);
        String data  = sp.getString(key,"");
        return data;
    }

    public boolean getBoolean(String key){
        SharedPreferences sp = context.getSharedPreferences("filename", Context.MODE_PRIVATE);
        Boolean data  = sp.getBoolean(key,false);
        return data;
    }

    public int getInt(String key){
        SharedPreferences sp = context.getSharedPreferences("filename",Context.MODE_PRIVATE);
        int data = sp.getInt(key,0);
        return data;
    }

    public float getFloat(String key){
        SharedPreferences sp = context.getSharedPreferences("filename",Context.MODE_PRIVATE);
        float data = sp.getFloat(key,0);
        return data;
    }

    public long getLong(String key){
        SharedPreferences sp = context.getSharedPreferences("filename",Context.MODE_PRIVATE);
        long data = sp.getLong(key,0);
        return data;
    }

    public Set getSet(String key,Set<String> set){
        SharedPreferences sp = context.getSharedPreferences("filename",Context.MODE_PRIVATE);
        Set<String> data = sp.getStringSet(key,set);
        return data;
    }
}