package com.app.bm.bm.fragment.personal;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bm.bm.MainActivity;
import com.app.bm.bm.R;

public class PersonalMenuAlreadLoggedFragment extends Fragment {
    private RelativeLayout itemMyOrder;         //我的订单
    private RelativeLayout itemMyCoupon;        //我的优惠券
    private RelativeLayout itemMyGold;          //我的金币
    private RelativeLayout itemMyCollection;    //我的收藏
    private RelativeLayout itemCommonTravel;    //常用出行人
    private RelativeLayout itemMyFreeTravel;    //我的0元游
    private RelativeLayout itemInviteBack;      //邀请返现

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate){
        View rootView = inflater.inflate(R.layout.seg_personal_menu_alreadylogged,null);

        //获取页面中的指定元素
        itemMyOrder = (RelativeLayout) rootView.findViewById(R.id.item_my_order);
        itemMyCoupon = (RelativeLayout) rootView.findViewById(R.id.item_my_coupon);
        itemMyGold = (RelativeLayout) rootView.findViewById(R.id.item_my_gold);
        itemMyCollection = (RelativeLayout) rootView.findViewById(R.id.item_my_collection);
        itemCommonTravel = (RelativeLayout) rootView.findViewById(R.id.item_common_travel);
        itemMyFreeTravel = (RelativeLayout) rootView.findViewById(R.id.item_my_free_travel);
        itemInviteBack = (RelativeLayout) rootView.findViewById(R.id.item_invite_back);

        //给页面中的元素添加点击事件监听
        itemMyOrder.setOnClickListener(onClickListener);
        itemMyCoupon.setOnClickListener(onClickListener);
        itemMyGold.setOnClickListener(onClickListener);
        itemMyCollection.setOnClickListener(onClickListener);
        itemCommonTravel.setOnClickListener(onClickListener);
        itemMyFreeTravel.setOnClickListener(onClickListener);
        itemInviteBack.setOnClickListener(onClickListener);

        return rootView;
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //处理点击事件
            Log.i("xiaobaicai","点击了personal已经登录的菜单中的一个item");
        }
    };
}