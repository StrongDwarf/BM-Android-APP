package com.app.bm.bm.main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.app.bm.bm.R;
import com.app.bm.bm.common.extend.AppCompatActivityWithNetWork;
import com.app.bm.bm.common.tools.SDCardTools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.File;


/**
 * @author xiaobaicai
 * @create time 2018-12-27
 */
public class MainActivity extends AppCompatActivity {
    //要切换显示的五个fragment
    private MainCustomizeFragment customizeFragment;
    private MainFindFragment discoverFragment;
    private MainHomeFragment1 indexFragment;
    private MainDestinationFragment locationFragment;
    private MainPersonalFragment personalFragment;

    private int currentId = R.id.tv_index;// 当前选中id,默认是主页

    private TextView tvIndex,tvLocation,tvPerson,tvDiscover,tvCustomize;//底部四个TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvIndex = (TextView) findViewById(R.id.tv_index);
        tvIndex.setSelected(true);//首页默认选中
        tvLocation = (TextView) findViewById(R.id.tv_location);
        tvPerson = (TextView) findViewById(R.id.tv_personal);
        tvDiscover = (TextView ) findViewById(R.id.tv_discover);
        tvCustomize = (TextView) findViewById(R.id.tv_customize);

        //默认加载首页
        indexFragment = new MainHomeFragment1();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container,indexFragment).commit();

        tvIndex.setOnClickListener(tabClickListener);
        tvLocation.setOnClickListener(tabClickListener);
        tvPerson.setOnClickListener(tabClickListener);
        tvDiscover.setOnClickListener(tabClickListener);
        tvCustomize.setOnClickListener(tabClickListener);
         int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;


        while(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }


    }


    private View.OnClickListener tabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() != currentId) {//如果当前选中跟上次选中的一样,不需要处理
                changeSelect(v.getId());//改变图标跟文字颜色的选中
                changeFragment(v.getId());//fragment的切换
                currentId = v.getId();//设置选中id
            }
        }
    };

    /**
     * 改变fragment的显示
     * @param resId
     */
    private void changeFragment(int resId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();//开启一个Fragment事务

        hideFragments(transaction);//隐藏所有fragment
        if(resId==R.id.tv_index){//主页
            if(indexFragment==null){//如果为空先添加进来.不为空直接显示
                indexFragment = new MainHomeFragment1();
                transaction.add(R.id.main_container,indexFragment);
            }else {
                transaction.show(indexFragment);//显示首页的Fragment
            }
        }else if(resId==R.id.tv_location){//动态
            if(locationFragment==null){
                locationFragment = new MainDestinationFragment();
                transaction.add(R.id.main_container,locationFragment);
            }else {
                transaction.show(locationFragment);
            }
        }else if(resId==R.id.tv_personal){//消息中心
            if(personalFragment==null){
                personalFragment = new MainPersonalFragment();
                transaction.add(R.id.main_container,personalFragment);
            }else {
                transaction.show(personalFragment);
            }
        }else if(resId==R.id.tv_discover){//我
            if(discoverFragment==null){
                discoverFragment = new MainFindFragment();
                transaction.add(R.id.main_container,discoverFragment);
            }else {
                transaction.show(discoverFragment);
            }
        }else if(resId == R.id.tv_customize){
            if(customizeFragment==null){
                customizeFragment = new MainCustomizeFragment();
                transaction.add(R.id.main_container,customizeFragment);
            }else {
                transaction.show(customizeFragment);
            }
        }
        transaction.commit();//一定要记得提交事务
    }

    /**
     * 显示之前隐藏所有fragment
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction){
        if (indexFragment != null)//不为空才隐藏,如果不判断第一次会有空指针异常
            transaction.hide(indexFragment);
        if (locationFragment != null)
            transaction.hide(locationFragment);
        if (personalFragment != null)
            transaction.hide(personalFragment);
        if (discoverFragment != null)
            transaction.hide(discoverFragment);
        if (customizeFragment != null)
            transaction.hide(customizeFragment);
    }

    /**
     * 改变TextView选中颜色
     * @param resId
     */
    private void changeSelect(int resId){
        tvIndex.setSelected(false);
        tvLocation.setSelected(false);
        tvPerson.setSelected(false);
        tvDiscover.setSelected(false);
        tvCustomize.setSelected(false);
        switch (resId) {
            case R.id.tv_index:
                tvIndex.setSelected(true);
                break;
            case R.id.tv_location:
                tvLocation.setSelected(true);
                break;
            case R.id.tv_personal:
                tvPerson.setSelected(true);
                break;
            case R.id.tv_discover:
                tvDiscover.setSelected(true);
                break;
            case R.id.tv_customize:
                tvCustomize.setSelected(true);
                break;
        }
    }


}
