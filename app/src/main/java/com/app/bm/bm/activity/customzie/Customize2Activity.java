package com.app.bm.bm.activity.customzie;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.app.bm.bm.R;
import com.app.bm.bm.adapter.LocationListviewAdapter;
import com.app.bm.bm.dataitem.CustomizeOrderData;
import com.app.bm.bm.entity.ButtonItem;
import com.app.bm.bm.entity.LocationItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Customize2Activity extends FragmentActivity {
    private Toolbar toolbar;        //顶部导航条
    private TextView tvAdultNum;    //成人数
    private TextView tvChildNum;    //儿童数
    private CustomizeOrderData customizeOrderData;  //保存数据信息


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT_WATCH){
            //版本号大于4.4
            Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);
            getWindow().setEnterTransition(slide);
        }
        setContentView(R.layout.activity_customize2);

        //初始化导航条
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left);
        toolbar.setNavigationOnClickListener(navOnClickListener);


        //添加事件监听
        findViewById(R.id.select_city).setOnClickListener(onClickListener);
        findViewById(R.id.select_date).setOnClickListener(onClickListener);
        findViewById(R.id.reduce_adultnum).setOnClickListener(onClickListener);
        findViewById(R.id.add_adultnum).setOnClickListener(onClickListener);
        findViewById(R.id.reduce_childnum).setOnClickListener(onClickListener);
        findViewById(R.id.add_childnum).setOnClickListener(onClickListener);

        //添加底部按钮监听
        findViewById(R.id.next_step).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                //下一页
                case R.id.next_step:
                    Intent intent = new Intent(Customize2Activity.this,Customize3Activity.class);
                    startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(Customize2Activity.this).toBundle());
                    break;
                case R.id.select_city:
                    //选择出发城市
                    break;
                case R.id.select_date:
                    //选择出发归来日期
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

    public static void showTimePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        // 解释一哈，Activity是context的子类
        new TimePickerDialog(activity, themeResId,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tv.setText("您选择了：" + hourOfDay + "时" + minute + "分");
                    }
                }

                ,calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                , true).show();
    }
}