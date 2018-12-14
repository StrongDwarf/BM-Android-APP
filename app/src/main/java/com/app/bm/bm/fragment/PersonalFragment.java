package com.app.bm.bm.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.support.v7.widget.Toolbar;

import com.app.bm.bm.MainActivity;
import com.app.bm.bm.R;
import com.app.bm.bm.fragment.personal.PersonalMenuAlreadLoggedFragment;
import com.app.bm.bm.fragment.personal.PersonalMenuDefaultFragment;
import com.app.bm.bm.fragment.personal.PersonalUserintrAlreadLoggedFragment;
import com.app.bm.bm.fragment.personal.PersonalUserintrDefaultFragment;

public class PersonalFragment extends Fragment {

    private Boolean loginState;        //登录状态， true:已登录  false:未登录

    private Toolbar toolbar;            //顶部导航条
    private RelativeLayout itemAboutBm;  //页面元素"关于斑马"
    private RelativeLayout itemConnactService;  //页面元素"联系客服"
    private RelativeLayout itemGotoAppstore;    //页面元素"前往AppStore评价"
    private PersonalMenuAlreadLoggedFragment personalMenuAlreadLoggedFragment;      //已经登录后的菜单页面
    private PersonalMenuDefaultFragment personalMenuDefaultFragment;                //没有登录时的菜单页面
    private PersonalUserintrAlreadLoggedFragment personalUserintrAlreadLoggedFragment;  //已经登录时候的个人头像页
    private PersonalUserintrDefaultFragment personalUserintrDefaultFragment;            //还没有登录时的个人头像页

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate){
        View rootView = inflater.inflate(R.layout.fragment_personal,null);

        //获取页面元素
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        itemAboutBm = (RelativeLayout) rootView.findViewById(R.id.item_about_bm);
        itemConnactService = (RelativeLayout) rootView.findViewById(R.id.item_connact_service);
        itemGotoAppstore = (RelativeLayout) rootView.findViewById(R.id.item_goto_appstore);

        //fragmentInit()  根据用户是否登录加载不同fragment
        fragmentInit();

        //设置顶部toolbar事件
        toolbar.inflateMenu(R.menu.bm_toolbar_menu);
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);


        //设置页面元素鼠标监听事件
        itemAboutBm.setOnClickListener(onClickListener);
        itemGotoAppstore.setOnClickListener(onClickListener);
        itemConnactService.setOnClickListener(onClickListener);

        //

        return rootView;
    }

    public Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Log.i("xiaobaicai","点击了toolbar上的菜单");
            return true;
        }
    };

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("xiaobaicai","点击了personal fragment上的元素");
        }
    };

    //根据用户是否登录加载不同fragment
    public void fragmentInit(){
        //获取登录状态
        SharedPreferences sp1 = this.getActivity().getSharedPreferences("filename",Context.MODE_PRIVATE);
        loginState = sp1.getBoolean("login_state",false);

        //开启一个Fragment事务
        FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();

        //隐藏所有fragment
        hideFragments(transaction);

        //根据登录状态初始化fragment，
        if(loginState)
        {
            if(personalUserintrAlreadLoggedFragment == null){
                personalUserintrAlreadLoggedFragment = new PersonalUserintrAlreadLoggedFragment();
                transaction.add(R.id.fra_user_head,personalUserintrAlreadLoggedFragment);
            }else{
                transaction.show(personalUserintrAlreadLoggedFragment);
            }
            if(personalMenuAlreadLoggedFragment == null){
                personalMenuAlreadLoggedFragment = new PersonalMenuAlreadLoggedFragment();
                transaction.add(R.id.fra_menu,personalMenuAlreadLoggedFragment);
            }else{
                transaction.show(personalMenuAlreadLoggedFragment);
            }
        }
        else
        {
            if(personalUserintrDefaultFragment == null){
                personalUserintrDefaultFragment = new PersonalUserintrDefaultFragment();
                transaction.add(R.id.fra_user_head,personalUserintrDefaultFragment);
            }else{
                transaction.show(personalUserintrDefaultFragment);
            }
            if(personalMenuDefaultFragment == null){
                personalMenuDefaultFragment = new PersonalMenuDefaultFragment();
                transaction.add(R.id.fra_menu,personalMenuDefaultFragment);
            }else{
                transaction.show(personalMenuDefaultFragment);
            }
        }

        //提交事务
        transaction.commit();

    }

    private void hideFragments(FragmentTransaction transaction){
        if(personalMenuAlreadLoggedFragment != null)
            transaction.hide(personalMenuAlreadLoggedFragment);
        if(personalMenuDefaultFragment != null)
            transaction.hide(personalMenuDefaultFragment);
        if(personalUserintrAlreadLoggedFragment != null)
            transaction.hide(personalUserintrAlreadLoggedFragment);
        if(personalUserintrDefaultFragment !=null)
            transaction.hide(personalUserintrDefaultFragment);
    }

}