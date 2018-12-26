package com.app.bm.bm.common;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.app.bm.bm.common.Services.getImage;

/**
 * 网络操作类,封装get,post请求。
 *
 */
public class Ajax{

    /**
     * get方法获取请求
     */
    public static void get(String path, Object o, Class cls, Handler handler){
        //get方式提交就是url拼接的方式
        //String path="http://192.168.43.74:8083/test?userid="+userid;

        String str = "";
        Log.i("xiaobaicai","start get");
        try{
            str =str + JSON.toString(o);
            Log.i("xiaobaicai",JSON.toString(o));
        }catch (Exception e){
            e.printStackTrace();
        }

        path = path +"?"+str;
        Log.i("xiaobaicai","path:"+path);
        try{
            /*
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);  //设置连接超时时间
            connection.setRequestMethod("GET");  //设置以Get方式提交数据
            Log.i("xiaobaicai","设置请求完毕");
            if(connection.getResponseCode() == 200){
                //请求成功
                Log.i("xiaobaicai","请求成功");
                InputStream is = connection.getInputStream();
                String resultStr = dealResponseResult(is);
                Object obj = JSON.parse(resultStr,cls);
                Message msg = new Message();
                msg.obj = obj;
                handler.sendMessage(msg);
            }*/
            Log.i("xiaobaicai","创建请求");
            HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            //System.out.println("tdw1");
            if(conn.getResponseCode() == 200){
                Log.i("xiaobaicai","请求成功");
                InputStream is = conn.getInputStream();

                String resultStr = dealResponseResult(is);
                Object obj = JSON.parse(resultStr,cls);
                Message msg = new Message();
                msg.obj = obj;
                handler.sendMessage(msg);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 处理服务器的响应结果(将输入流转化成字符串)
     * @param inputStream 服务器的响应输入流
     * @return
     */
    private static String dealResponseResult(InputStream inputStream){
        String resultData = null;  //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try{
            while ((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data,0,len);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }

}