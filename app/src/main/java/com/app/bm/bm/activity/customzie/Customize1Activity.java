package com.app.bm.bm.activity.customzie;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
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

public class Customize1Activity extends FragmentActivity {
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
        setContentView(R.layout.activity_customize1);

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
                    Intent intent = new Intent(Customize1Activity.this,Customize2Activity.class);
                    startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(Customize1Activity.this).toBundle());
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
                {"亚洲","欧美","海岛"},
                {"日本","巴厘岛","新加坡","越南","马来西亚","泰国","斯里兰卡","柬埔寨","坦桑尼亚","北极"},
                {"欧洲","美国","加拿大","南美","土耳其"},
                {"毛里求斯","马尔代夫","斐济","塞舌尔共和国","塞班岛"}};

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

        /*
        for(int i=0;i<5;i++){
            List<ButtonItem> buttonItems = new ArrayList<>();
            String[] lists = new String[]{"日本","巴厘岛","新加坡","越南","马来西亚","泰国","斯里兰卡","柬埔寨","坦桑尼亚","北极"};
            for(int j=0;j<lists.length;j++){
                ButtonItem buttomItem = new ButtonItem(j,lists[j]);
                buttonItems.add(buttomItem);
            }
            LocationItem locationItem = new LocationItem(i,"亚洲",buttonItems);
            locationItems.add(locationItem);
        }
        */
    }
}