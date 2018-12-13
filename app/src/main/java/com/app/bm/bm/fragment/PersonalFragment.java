package com.app.bm.bm.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.support.v7.widget.Toolbar;

import com.app.bm.bm.R;

public class PersonalFragment extends Fragment {
    private Toolbar toolbar;            //顶部导航条
    private FrameLayout fraUserHead;    //头部置换区域 -> seg_personal_userintr_alreadylogged.xml or seg_personal_menu_default
    private FrameLayout fraMenu;        //menu置换区域 -> seg_personal_menu_alreadylogged.xml or seg_personal_menu_default
    private RelativeLayout itemAboutBm;  //页面元素"关于斑马"
    private RelativeLayout itemConnactService;  //页面元素"联系客服"
    private RelativeLayout itemGotoAppstore;    //页面元素"前往AppStore评价"

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate){
        View rootView = inflater.inflate(R.layout.fragment_personal,null);

        //获取页面元素
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        fraUserHead  = (FrameLayout) rootView.findViewById(R.id.fra_user_head);
        fraMenu = (FrameLayout) rootView.findViewById(R.id.fra_menu);
        itemAboutBm = (RelativeLayout) rootView.findViewById(R.id.item_about_bm);
        itemConnactService = (RelativeLayout) rootView.findViewById(R.id.item_connact_service);
        itemGotoAppstore = (RelativeLayout) rootView.findViewById(R.id.item_goto_appstore);

        //根据用户是否登录加载不同fragment
        /*
        if(isLogin)
        {

        }
        else
        {

        }
        */


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
}