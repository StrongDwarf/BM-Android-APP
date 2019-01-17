package com.app.bm.bm.common.extend;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;


import com.app.bm.bm.common.tools.SDCardTools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.File;

public class AppCompatActivityWithNetWork extends AppCompatActivity {

    //public static Ajax ajax = new Ajax();

    //输入网络请求中的path字段,输出应该用于映射到本地的path
    private String getDirPath(String path){
        String[] paths = path.trim().split("/");
        if(paths.length <= 2){
            return File.separator+"default";
        }else{
            String str = "";
            for(int i =1;i<paths.length-1;i++){
                str = str+File.separator+paths[i];
            }
            return str;
        }
    }

    //输入url，获取bitmap
    public void setBitImg(String url, final ImageView imageView, Context context){
        Uri uri = Uri.parse(url);                           //转化为url
        String path = uri.getPath();                        //获取url中的path
        String[] temp = path.split("/");
        final String dirPath = context.getExternalCacheDir().getAbsolutePath()+ getDirPath(path);//+ File.separator +temp[temp.length - 1];    //获取文件的本地位置
        final String fileName = temp[temp.length - 1];
        File file = new File(dirPath);
        if(!file.exists()){
            file.mkdirs(); //递归创建自定义目录
        }
        final File bitFile = new File(dirPath,fileName);
        if(bitFile.exists()){
            Bitmap bitmap =  SDCardTools.loadBitmapFromSDCard(dirPath+File.separator+fileName);
            imageView.setImageBitmap(bitmap);
        }else {
            OkHttpUtils.get().url(url).tag(this)
                    .build()
                    .connTimeOut(20000).readTimeOut(20000).writeTimeOut(20000)
                    .execute(new BitmapCallback() {
                        @Override
                        public void onError(okhttp3.Call call, Exception e, int id) {
                            Log.e("xiaobaicai", e.toString());

                        }
                        @Override
                        public void onResponse(final Bitmap response, int id) {
                            SDCardTools.saveBitmapToSDCardDir(response, dirPath, fileName, getBaseContext());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    imageView.setImageBitmap(response);
                                }
                            });
                        }
                    });
        }
    }

    //输入url，和bitmap对象,将url指定的bitmap赋给bitmap
    public Bitmap getBitImg(String url, Context context){
        Uri uri = Uri.parse(url);                           //转化为url
        String path = uri.getPath();                        //获取url中的path
        String[] temp = path.split("/");
        Bitmap bitmap = null;
        final String dirPath = context.getExternalCacheDir().getAbsolutePath()+ getDirPath(path);//+ File.separator +temp[temp.length - 1];    //获取文件的本地位置
        final String fileName = temp[temp.length - 1];
        File file = new File(dirPath);
        if(!file.exists()){
            file.mkdirs(); //递归创建自定义目录
        }
        final File bitFile = new File(dirPath,fileName);
        /*
        if(bitFile.exists()){
            bitmap =  SDCardTools.loadBitmapFromSDCard(dirPath+File.separator+fileName);
        }else {
            OkHttpUtils.get().url(url).tag(this)
                    .build()
                    .connTimeOut(20000).readTimeOut(20000).writeTimeOut(20000)
                    .execute(new BitmapCallback() {
                        @Override
                        public void onError(okhttp3.Call call, Exception e, int id) {
                            Log.e("xiaobaicai", e.toString());
                        }
                        @Override
                        public void onResponse(final Bitmap response, int id) {
                            SDCardTools.saveBitmapToSDCardDir(response, dirPath, fileName, getBaseContext());
                            bitmap = response;
                    }
                    });
        }
        */

        if(!bitFile.exists()){
            OkHttpUtils.get().url(url).tag(this)
                    .build()
                    .connTimeOut(20000).readTimeOut(20000).writeTimeOut(20000)
                    .execute(new BitmapCallback() {
                        @Override
                        public void onError(okhttp3.Call call, Exception e, int id) {
                            Log.e("xiaobaicai", e.toString());
                        }
                        @Override
                        public void onResponse(final Bitmap response, int id) {
                            SDCardTools.saveBitmapToSDCardDir(response, dirPath, fileName, getBaseContext());
                        }
                    });
        }
        bitmap =  SDCardTools.loadBitmapFromSDCard(dirPath+File.separator+fileName);
        return bitmap;
    }


}