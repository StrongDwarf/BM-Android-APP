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

import com.app.bm.bm.R;
import com.app.bm.bm.adapter.LocationListviewAdapter;
import com.app.bm.bm.entity.ButtonItem;
import com.app.bm.bm.entity.LocationItem;

import java.util.ArrayList;
import java.util.List;

public class Customize2Activity extends FragmentActivity {
    private Toolbar toolbar;        //顶部导航条


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

        //添加底部按钮监听
        findViewById(R.id.next_step).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.next_step:
                    Intent intent = new Intent(Customize2Activity.this,Customize3Activity.class);
                    startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(Customize2Activity.this).toBundle());
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

}