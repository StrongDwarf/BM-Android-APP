package com.app.bm.bm.common.tools;

import android.util.Log;

import com.app.bm.bm.common.datas.AjaxReceiveData;
import com.app.bm.bm.common.debuger.UtilLog;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Ajax,封装get和post请求
 */
public class Ajax{

    private static  Gson gson = new Gson();

    public static AjaxReceiveData post(Object sendObj, String urlApi){
        String res = "";

        try{
            URL url = new URL(urlApi);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000); //设置连接超时时间
            connection.setRequestMethod("POST");
            Log.i("xiaobaicai",sendObj.toString());
            String data = getBefore(sendObj);

            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length",data.length()+"");
            Log.i("xiaobaicai","POST请求地址:"+url.getHost()+url.getPath().toString());
            Log.i("xiaobaicai","POST发送数据:"+data);

            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes());

            if(connection.getResponseCode() == 200){
                InputStream is = connection.getInputStream();
                res = dealResponseResylt(is);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Log.i("xiaobaicai","POST RES:"+res);
        AjaxReceiveData ajaxReceiveData = gson.fromJson(res,AjaxReceiveData.class);
        //Log.i("xiaobaicai","code:"+ajaxReceiveData.getCode());
        return ajaxReceiveData;
    }

    /**
     * get方法获取数据
     *
     * @param sendObj 发送的对象
     * @param urlApi   urlAp
     * @returni
     */
    public static AjaxReceiveData get(Object sendObj,String urlApi){
        String query = getBefore(sendObj);
        Log.i("xiaobaicai","请求字符串:"+query);
        String path = urlApi + "?"+query;
        Log.i("xiaobaicai","请求："+path);
        //String res = "";
        String res = new String();
        try{
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);     //设置连接超时时间
            connection.setRequestMethod("GET");
            if(connection.getResponseCode() == 200){//请求成功
                Log.i("xiaobaicai","请求成功");

                //int fileLength = connection.getContentLength();
                InputStream is = connection.getInputStream();
                res =  dealResponseResylt(is);
            }
        }catch (Exception e){
            Log.i("xiaobaicai","网络请求出错");
            e.printStackTrace();
        }
        UtilLog.i("xiaobaicai","GET返回:"+res);
        //res = "{\"code\":4,\"message\":\"\\u624b\\u673a\\u53f7\\u672a\\u6ce8\\u518c\",\"data\":{\"name\":\"xiaobaicai\"}}";
        AjaxReceiveData ajaxReceiveData = gson.fromJson(res,AjaxReceiveData.class);
        //Log.i("xiaobaicai","code:"+ajaxReceiveData.getCode());
        return ajaxReceiveData;
    }

    /**
     * get方法获取数据
     *
     * @param sendObj 发送的对象
     * @param urlApi   urlApi
     * @param getObjCls 获取数据后保存的Obj对象class
     * @return
     */
    public static AjaxReceiveData get(Object sendObj,String urlApi,Class getObjCls){
        String query = getBefore(sendObj);
        Log.i("xiaobaicai","请求字符串:"+query);
        String path = urlApi + "?"+query;
        Log.i("xiaobaicai","请求："+path);
        String res = "";
        try{
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);     //设置连接超时时间
            connection.setRequestMethod("GET");
            if(connection.getResponseCode() == 200){//请求成功
                Log.i("xiaobaicai","请求成功");
                InputStream is = connection.getInputStream();
                res =  dealResponseResylt(is);
            }
        }catch (Exception e){
            Log.i("xiaobaicai","网络请求出错");
            e.printStackTrace();
        }
        UtilLog.i("xiaobaicai",res);
        //res = "{\"code\":4,\"message\":\"\\u624b\\u673a\\u53f7\\u672a\\u6ce8\\u518c\",\"data\":{\"name\":\"xiaobaicai\"}}";
        AjaxReceiveData ajaxReceiveData = gson.fromJson(res,AjaxReceiveData.class);
        //Object ajaxData = gson.fromJson(ajaxReceiveData.getData().toString(),getObjCls);
        //ajaxReceiveData.setData(ajaxData);
        return ajaxReceiveData;
    }



    /**
     * 发送get请求前对将obj对象转化成字符串
     *
     * @param obj
     * @return
     */
    private static String getBefore(Object obj){
        String restStr = "";
        Field[] fields = obj.getClass().getDeclaredFields();
        for(int i = 0 , len = fields.length; i < len; i++) {
            // 对于每个属性，获取属性名
            String propertyName = fields[i].getName();
            Log.i("xiaobaicai","属性名:"+propertyName);
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object propertyValue;
                try {
                    propertyValue = fields[i].get(obj);
                    if(propertyName.toString() !="$change" && propertyName.toString() != "serialVersionUID" && propertyName.toString() != "this$0"){
                        restStr = restStr + propertyName + "="+ propertyValue+"&";
                    }

                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }
        return restStr;
    }

    /**
     * 处理服务器的响应结果(将输入流转化成字符串)
     * @param inputStream 服务器的响应输入流
     * @return
     */
    private static String dealResponseResylt(InputStream inputStream){
        String resultData = null;       //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[102400];
        int len = 0;
        try{
            while((len = inputStream.read(data)) != -1){
                byteArrayOutputStream.write(data,0,len);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }

    private static String dealResponseResylt(InputStream inputStream,int length){
        String resultData = null;       //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try{
            while((len = inputStream.read(data)) != -1){
                byteArrayOutputStream.write(data,0,len);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }
}