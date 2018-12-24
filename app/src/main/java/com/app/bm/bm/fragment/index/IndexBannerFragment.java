package com.app.bm.bm.fragment.index;

import android.content.Context;
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
import com.app.bm.bm.entity.HomeBannerItem;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 17/5/31.
 */

public class IndexBannerFragment extends Fragment {
    public static final String TAG = "xiaobaicai";
    public static final int []BANNER = new int[]{R.mipmap.banner1,R.mipmap.banner2,R.mipmap.banner3,R.mipmap.banner4,R.mipmap.banner5};
    private MZBannerView mMZBanner;

    private List<HomeBannerItem> homeBannerItemList;

    private TextView tvBannerTitle;
    private TextView tvBannerText;
    private int curPosition;

    public static IndexBannerFragment newInstance(){
        return new IndexBannerFragment();
    }

    private void initView(View view) {


        mMZBanner = (MZBannerView) view.findViewById(R.id.banner);
        mMZBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                Toast.makeText(getContext(),"click page:"+position,Toast.LENGTH_LONG).show();
            }
        });
        mMZBanner.addPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position != curPosition){
                    tvBannerTitle.setText(homeBannerItemList.get(position).getTitle());
                    tvBannerText.setText(homeBannerItemList.get(position).getIntro());
                    curPosition = position;
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG,"addPageChangeLisnter:"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //初始化数据
        homeBannerItemList = new ArrayList<>();
        String title = "火烈鸟|呼唤大自然美妙乐章";
        String text = "坦桑尼亚最精彩最便利的火烈鸟观赏之地,非洲青山掩映中，精灵之鸟的动人故事!";
        String innerTitle = "The Cinematic Orchestra";
        String innerIntro = "Les Ailes Pourpres";
        String id = "0";
        for(int i=0;i<BANNER.length;i++){
            homeBannerItemList.add(new HomeBannerItem(id+i,BANNER[i],title+i,text+i,innerTitle+i,innerIntro+i));
        }

        mMZBanner.setIndicatorVisible(false);
        mMZBanner.setPages(homeBannerItemList, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

        //初始化banner下面的文字信息
        tvBannerTitle.setText(homeBannerItemList.get(curPosition).getTitle());
        tvBannerText.setText(homeBannerItemList.get(curPosition).getIntro());
        curPosition = 0;
    }


    public static class BannerViewHolder implements MZViewHolder<HomeBannerItem> {
        private ImageView mImageView;       //图片
        private TextView  titleView;        //内部标题
        private TextView  textView;         //内部正文

        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item_padding,null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            titleView = (TextView) view.findViewById(R.id.banner_inner_title);
            textView = (TextView) view.findViewById(R.id.banner_inner_text);
            return view;
        }

        @Override
        public void onBind(Context context, int position, HomeBannerItem data) {
            // 数据绑定
            mImageView.setImageResource(data.getImgData());
            titleView.setText(data.getInnerTitle());
            textView.setText(data.getInnerIntro());
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
