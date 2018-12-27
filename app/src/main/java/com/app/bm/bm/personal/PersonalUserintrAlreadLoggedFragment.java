package com.app.bm.bm.personal;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.app.bm.bm.R;

public class PersonalUserintrAlreadLoggedFragment extends Fragment {
    private ImageView userPic;              //头像图片
    private LinearLayout userPicRight;      //头像图片右边的元素

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate){
        View rootView = inflater.inflate(R.layout.personal_seg_userlntr_alreadylogged,null);

        //获取页面中的指定元素
        userPic = (ImageView) rootView.findViewById(R.id.user_pic);
        userPicRight = (LinearLayout) rootView.findViewById(R.id.user_pic_right);

        //给页面中的元素添加点击事件监听
        userPic.setOnClickListener(onClickListener);
        userPicRight.setOnClickListener(onClickListener);

        return rootView;
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //处理点击事件
            Log.i("xiaobaicai","点击了头像框中的一个元素");
        }
    };
}