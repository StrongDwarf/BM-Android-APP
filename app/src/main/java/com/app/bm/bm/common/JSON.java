package com.app.bm.bm.common;

import com.google.gson.Gson;

import java.lang.reflect.Field;

/**
 * 函数内部类：字符串操作函数
 *
 */
public class JSON{

    /**
     * 将对象转换成String
     *
     * @param o
     * @return   返回的格式如 "name=xiaobaicai&age=13"
     * @throws Exception
     */
    public static String toString(Object o) throws Exception{
        String str = "";
        for (Field field : o.getClass().getDeclaredFields()){
            field.setAccessible(true);
            str = field.getName() + "=" + field.get(o)+"&";
        }
        return str;
    }

    public static Object parse(String str,Class cls){
        Object obj = (new Gson()).fromJson(str,cls);
        return obj;
    }

}