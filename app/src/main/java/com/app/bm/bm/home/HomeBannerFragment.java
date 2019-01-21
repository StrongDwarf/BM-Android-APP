package com.app.bm.bm.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bm.bm.R;
import com.app.bm.bm.common.apiUrls.ApiUrl;
import com.app.bm.bm.common.datas.AjaxReceiveData;
import com.app.bm.bm.common.datas.HomeBannerItemData;
import com.app.bm.bm.common.items.HomeBannerItem;
import com.app.bm.bm.common.tools.Ajax;
import com.bumptech.glide.Glide;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 17/5/31.
 */

public class HomeBannerFragment extends Fragment {
    public static final String TAG = "xiaobaicai";
    private MZBannerView mMZBanner;

    private List<HomeBannerItemData> homeBannerItemList;

    private List<BannerViewHolder> bannerViewHolderList = new ArrayList<BannerViewHolder>();

    private TextView tvBannerTitle;
    private TextView tvBannerText;
    private int curPosition;

    public HomeBannerFragment(){}

    @SuppressLint("ValidFragment")
    public HomeBannerFragment(List<HomeBannerItemData> homeBannerItemList){
        this.homeBannerItemList = homeBannerItemList;
    }

    public static HomeBannerFragment newInstance(){
        return new HomeBannerFragment();
    }

    private void initView(View view) {

        mMZBanner = (MZBannerView) view.findViewById(R.id.banner);
        mMZBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                Toast.makeText(getContext(),"click page:"+position+"Url"+homeBannerItemList.get(position).getUrl(),Toast.LENGTH_LONG).show();
            }
        });
        mMZBanner.addPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position != curPosition){
                    tvBannerTitle.setText(homeBannerItemList.get(position).getTitle());
                    tvBannerText.setText(homeBannerItemList.get(position).getSubtitle());
                    curPosition = position;
                }
            }

            @Override
            public void onPageSelected(int position) {
                // Log.e(TAG,"addPageChangeLisnter:"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mMZBanner.setIndicatorVisible(false);
        mMZBanner.setPages(homeBannerItemList, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                BannerViewHolder bannerViewHolder = new BannerViewHolder();
                bannerViewHolderList.add(bannerViewHolder);
                return bannerViewHolder;
            }
        });

        //初始化banner下面的文字信息
        tvBannerTitle.setText(homeBannerItemList.get(curPosition).getTitle());
        tvBannerText.setText(homeBannerItemList.get(curPosition).getSubtitle());
        curPosition = 0;
    }

    public static class BannerViewHolder implements MZViewHolder<HomeBannerItemData> {
        private ImageView mImageView;       //图片
        private TextView  titleView;        //内部标题
        private TextView  textView;         //内部正文

        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item_padding,null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            // titleView = (TextView) view.findViewById(R.id.banner_inner_title);
            // textView = (TextView) view.findViewById(R.id.banner_inner_text);
            return view;
        }

        @Override
        public void onBind(Context context, int position, HomeBannerItemData data) {
            // 数据绑定
            //mImageView.setImageBitmap(data.getImgData());
            Glide.with(context).load(data.getImg()).placeholder(R.mipmap.banner1).into(mImageView);
            // titleView.setText(data.getInnerTitle());
            // textView.setText(data.getInnerIntro());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index_banner,null);

        //获取页面中tv_title tv_text元素
        tvBannerTitle = view.findViewById(R.id.tv_banner_title);
        tvBannerText = view.findViewById(R.id.tv_banner_text);

        initView(view);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        mMZBanner.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMZBanner.start();
    }
}