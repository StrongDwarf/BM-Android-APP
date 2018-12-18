package com.app.bm.bm.activity.customzie;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.ListView;

import com.app.bm.bm.MainActivity;
import com.app.bm.bm.R;
import com.app.bm.bm.adapter.LocationListviewAdapter;
import com.app.bm.bm.entity.ButtonItem;
import com.app.bm.bm.entity.ElemListView;
import com.app.bm.bm.entity.LocationItem;

import java.util.ArrayList;
import java.util.List;

public class Customize3Activity extends FragmentActivity {
    private Toolbar toolbar;        //顶部导航条
    private ElemListView listView;      //页面显示区域
    private LocationListviewAdapter locationListviewAdapter;        //适配器
    private List<LocationItem> locationItems;           //数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT_WATCH){
            //版本号大于4.4
            Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);
            getWindow().setEnterTransition(slide);
        }
        setContentView(R.layout.activity_customize3);

        //初始化数据
        initData();

        //初始化listview
        listView = (ElemListView) findViewById(R.id.location_listview);
        locationListviewAdapter = new LocationListviewAdapter(this,locationItems);
        listView.setAdapter(locationListviewAdapter);

        //初始化导航条
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left);
        toolbar.setNavigationOnClickListener(navOnClickListener);

        //添加底部按钮监听
        findViewById(R.id.next_step).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.next_step:
                    /*
                    Intent intent = new Intent(Customize1Activity.this,Customize2Activity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                    */
                    Intent intent = new Intent(Customize3Activity.this,Customize4Activity.class);
                    startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(Customize3Activity.this).toBundle());
                    break;
            }
        }
    };
    View.OnClickListener navOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    //初始化数据
    private void initData(){
        String[][] locationDatalists = new String[][]{
                {"机票","酒店","房型","餐食","用车","签证"},
                {"经济仓","商务仓","头等仓","自理"},
                {"标准五星","豪华五星","四星","三星","名宿","自理"},
                {"标准房","豪华房","海景房","家庭房","套房"},
                {"中餐","当地风味餐","米其林餐","自理"},
                {"全程包车","半包车","无需包车"},
                {"代办签证","自理签证"}
        };

        locationItems=new ArrayList<>();
        int length = locationDatalists[0].length+1;
        for(int i=1;i<length;i++){
            List<ButtonItem> buttonItems = new ArrayList<>();
            int temp = locationDatalists[i].length - 1;
            for(int j=0;j<temp;j++){
                ButtonItem buttomItem = new ButtonItem(j,locationDatalists[i][j]);
                buttonItems.add(buttomItem);
            }
            LocationItem locationItem = new LocationItem(i,locationDatalists[0][i-1],buttonItems);
            locationItems.add(locationItem);
        }


    }
}