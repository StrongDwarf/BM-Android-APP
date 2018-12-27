package com.app.bm.bm.personal;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.bm.bm.R;

public class PersonalMenuDefaultFragment extends Fragment {
    private RelativeLayout itemMyOrder;         //我的订单
    private RelativeLayout itemMyCoupon;        //我的优惠券
    private RelativeLayout itemMyGold;          //我的金币
    private RelativeLayout itemMyCollection;    //我的收藏

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate){
        View rootView = inflater.inflate(R.layout.personal_seg_menu_default,null);

        //获取页面中的指定元素
        itemMyOrder = (RelativeLayout) rootView.findViewById(R.id.item_my_order);
        itemMyCoupon = (RelativeLayout) rootView.findViewById(R.id.item_my_coupon);
        itemMyGold = (RelativeLayout) rootView.findViewById(R.id.item_my_gold);
        itemMyCollection = (RelativeLayout) rootView.findViewById(R.id.item_my_collection);

        //给页面中的元素添加点击事件监听
        itemMyOrder.setOnClickListener(onClickListener);
        itemMyCoupon.setOnClickListener(onClickListener);
        itemMyGold.setOnClickListener(onClickListener);
        itemMyCollection.setOnClickListener(onClickListener);

        return rootView;
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //处理点击事件
            Log.i("xiaobaicai","点击了personal默认的菜单中的一个item");
        }
    };
}