package com.app.bm.bm.find;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.bm.bm.R;
import com.app.bm.bm.common.adapter.FindAdapter;
import com.app.bm.bm.common.items.FindItem;

import java.util.ArrayList;
import java.util.List;

/*
public class FindAllFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate){
        View rootView = inflater.inflate(R.layout.fragment_find_all,null);
        return rootView;
    }
}
*/
public class FindAllFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private FindAdapter findAdapter;
    int countA = 0;
    int countB = 0;

    //数据源
    private List<FindItem> findItemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate){
        View rootView = inflater.inflate(R.layout.find_fra_all,null);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.srl);
        //刷新监听事件
        swipeRefreshLayout.setOnRefreshListener(this);

        //swipeRefreshLayout.setScrollBarStyle()
        //设置进度圈的背景色
        //swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorAccent));
        //设置进度动画的颜色,可以使用多种颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary,R.color.colorPrimaryDark);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);


        linearLayoutManager = new LinearLayoutManager(rootView.getContext());

        findItemList = new ArrayList<FindItem>();
        //初始化adapter
        findAdapter = new FindAdapter(rootView.getContext());
        //设置RecyclerView属性为ListView
        recyclerView.setLayoutManager(linearLayoutManager);
        //加载适配器
        recyclerView.setAdapter(findAdapter);
        //添加滑动监听
        recyclerView.addOnScrollListener(onScrollListener);
        initADatas();

        return rootView;
    }

    //RecyclerView的滑动监听
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        private int lastVisibleItem;

        //滑动状态改变
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            /**
             * scrollState有三种状态，分别是SCROLL_STATE_IDLE、SCROLL_STATE_TOUCH_SCROLL、SCROLL_STATE_FLING
             * SCROLL_STATE_IDLE是当屏幕停止滚动时
             * SCROLL_STATE_TOUCH_SCROLL是当用户在以触屏方式滚动屏幕并且手指仍然还在屏幕上时
             * SCROLL_STATE_FLING是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
             */
            if(newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == findAdapter.getItemCount()
                    && findAdapter.isShowFooter())
            {
                //加载更多
                initADatas();
            }
        }

        //滑动位置
        @Override
        public void onScrolled(RecyclerView recyclerView,int dx,int dy)
        {
            super.onScrolled(recyclerView,dx,dy);
            //给lastVisibleItem赋值
            //findLastVisibleItemPosition()是返回最后一个item的位置
            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        }
    };

    //设置下拉刷新数据
    private void initBDatas()
    {
        for(int i=0;i<5;i++)
        {
            FindItem findItem = new FindItem(i,"text1A"+String.valueOf(countA),"text2A"+String.valueOf(countA),"看JJ","");
            findItemList.add(findItem);
            countA++;
        }
        findAdapter.setData(findItemList);

    }

    //设置上拉加载数据
    private void initADatas()
    {
        for(int i=0;i<5;i++)
        {
            FindItem findItem = new FindItem(i,"text1B"+String.valueOf(countA),"text2B"+String.valueOf(countA),"看JJ","");
            findItemList.add(findItem);
            countB++;
        }
        findAdapter.setData(findItemList);
    }

    //设置下拉刷新
    @Override
    public void onRefresh()
    {
        initBDatas();
        //关闭加载进度条
        swipeRefreshLayout.setRefreshing(false);
    }
}