package com.app.bm.bm;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bm.bm.fragment.CustomizeFragment;
import com.app.bm.bm.fragment.DiscoverFragment;
import com.app.bm.bm.fragment.IndexFragment;
import com.app.bm.bm.fragment.LocationFragment;
import com.app.bm.bm.fragment.PersonalFragment;

/**
 * 对fragment的切换,底部图标颜色的切换
 * @author ansen
 * @create time 2015-09-08
 */
public class MainActivity extends FragmentActivity {
    //要切换显示的四个Fragment

    private IndexFragment indexFragment;
    private LocationFragment locationFragment;
    private PersonalFragment personalFragment;
    private DiscoverFragment discoverFragment;
    private CustomizeFragment customizeFragment;

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
        indexFragment = new IndexFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container,indexFragment).commit();

        tvIndex.setOnClickListener(tabClickListener);
        tvLocation.setOnClickListener(tabClickListener);
        tvPerson.setOnClickListener(tabClickListener);
        tvDiscover.setOnClickListener(tabClickListener);
        tvCustomize.setOnClickListener(tabClickListener);
        //findViewById(R.id.iv_make).setOnClickListener(onClickListener);
    }

    /*
    private OnClickListener onClickListener=new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_make:
                    Toast.makeText(MainActivity.this,"点击了制作按钮",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
*/
    private OnClickListener tabClickListener = new OnClickListener() {
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
                indexFragment = new IndexFragment();
                transaction.add(R.id.main_container,indexFragment);
            }else {
                transaction.show(indexFragment);//显示首页的Fragment
            }
        }else if(resId==R.id.tv_location){//动态
            if(locationFragment==null){
                locationFragment = new LocationFragment();
                transaction.add(R.id.main_container,locationFragment);
            }else {
                transaction.show(locationFragment);
            }
        }else if(resId==R.id.tv_personal){//消息中心
            if(personalFragment==null){
                personalFragment = new PersonalFragment();
                transaction.add(R.id.main_container,personalFragment);
            }else {
                transaction.show(personalFragment);
            }
        }else if(resId==R.id.tv_discover){//我
            if(discoverFragment==null){
                discoverFragment = new DiscoverFragment();
                transaction.add(R.id.main_container,discoverFragment);
            }else {
                transaction.show(discoverFragment);
            }
        }else if(resId == R.id.tv_customize){
            if(customizeFragment==null){
                customizeFragment = new CustomizeFragment();
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
