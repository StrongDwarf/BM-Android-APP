package com.app.bm.bm.main;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bm.bm.R;
import com.app.bm.bm.common.adapter.IndexGridAdapter;
import com.app.bm.bm.common.apiUrls.ApiUrl;
import com.app.bm.bm.common.datas.AjaxReceiveData;
import com.app.bm.bm.common.datas.HomeActivityData;
import com.app.bm.bm.common.datas.HomeBannerItemData;
import com.app.bm.bm.common.datas.HomePageData;
import com.app.bm.bm.common.datas.ProductItemData;
import com.app.bm.bm.common.datas.ProductItemElemData;
import com.app.bm.bm.common.datas.ProductListItemData;
import com.app.bm.bm.common.datas.ProductMarkItem;
import com.app.bm.bm.common.extend.elem.ElemGridView;
import com.app.bm.bm.common.items.HomeBannerItem;
import com.app.bm.bm.common.items.IndexInterestItem;
import com.app.bm.bm.common.tools.Ajax;
import com.app.bm.bm.common.tools.Tool;
import com.app.bm.bm.home.HomeBannerFragment;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class MainHomeFragment1 extends Fragment{
    private ElemGridView indexGvIntexest;               //兴趣主题中的GridView页面
    private IndexGridAdapter indexGridAdapter;
    private List<IndexInterestItem> indexInterestItems;
    private Toolbar toolbar;                        //顶部导航条

    private LinearLayout lastedActivityContainer;            //近期活动

    private HomePageData homePageData;              //首页数据
   // private List<HomeBannerItemData> homeBannerList;        //首页Banner数据
    private LinearLayout llElemListViewContainer;           //首页,限定甄选父容器
    private LinearLayout banmaFindContainer;                //斑马发现父容器

    private HomeBannerFragment homeBannerFragment;
    public static final int UPDATE_BANNER=1;                //更新BANNER
    public static final int UPDATE_BANNER_DATA = 2;         //更新BANNER数据
    public static final int UPDATE_BANNER_ITEM_BITMAP = 3;          //更新BANNER item
    public static final int ADD_ACTIVITY_ITEM = 4;          //添加近期活动子元素
    public static final int ADD_PRODUCT_LIST = 5;           //添加产品列表
    public static final int UPDATE_PRODUCT_LIST_BITMAP = 6;    //更新产品列表中的图片,图片加载完成后执行
    public static final int ADD_BANMA_FIND_ITEM = 7;            //添加斑马发现子元素


    private LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_BANNER:
                    Log.i("xiaobaicai", "更新Banner");
                    HomeBannerFragment homeBannerFragment = new HomeBannerFragment((List<HomeBannerItemData>) msg.obj);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fra_index_banner, homeBannerFragment).commit();
                    break;
                case ADD_ACTIVITY_ITEM:
                    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    HomeActivityData homeActivityData = (HomeActivityData) msg.obj;
                    for(int i = 0;i<homeActivityData.getList().size();i++){
                        View view = inflater.inflate(R.layout.home_item_lasted_activity,null);
                        ImageView imageView = view.findViewById(R.id.iv_1);
                        TextView textView = view.findViewById(R.id.tv_1);
                        Glide.with(getContext()).load(homeActivityData.getList().get(i).getImg()).into(imageView);
                        textView.setText(homeActivityData.getList().get(i).getTitle());
                        lastedActivityContainer.addView(view);
                    }
                    break;
                case ADD_PRODUCT_LIST:
                    LayoutInflater inflater1 = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    List<ProductItemData> productItemDataList = (List<ProductItemData>) msg.obj;
                    addProductList(productItemDataList);
                    break;
                case ADD_BANMA_FIND_ITEM:
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate) {
        View rootView = inflater.inflate(R.layout.main_home_fragment, null);

        //获取相关页面元素
        lastedActivityContainer = rootView.findViewById(R.id.lasted_activity);  //近期活动
        lastedActivityContainer.removeAllViews();
        llElemListViewContainer = rootView.findViewById(R.id.ll_elemlistview_container);
        llElemListViewContainer.removeAllViews();

        //启动获取首页数据线程
        new GetHomePageDataThread().start();
        return rootView;
    }

    private class GetHomePageDataThread extends Thread{
        @Override
        public void run(){
            AjaxReceiveData homePageAfterData = Ajax.get(new getHomePageDataBefore(1), ApiUrl.getHomePage());
            if (homePageAfterData != null) {
                homePageData = (new Gson()).fromJson((new Gson()).toJson(((AjaxReceiveData) homePageAfterData).getData()), HomePageData.class);

                //处理Banner数据
                Message message = handler.obtainMessage();
                message.what=UPDATE_BANNER;
                message.obj = homePageData.getBanner().getList();
                handler.sendMessage(message);

                //处理近期活动数据
                Message message1 = handler.obtainMessage();
                message1.what=ADD_ACTIVITY_ITEM;
                message1.obj = homePageData.getActivity();
                handler.sendMessage(message1);

                //处理限定甄选等列表数据
                Message message2 = handler.obtainMessage();
                message2.what=ADD_PRODUCT_LIST;
                message2.obj = homePageData.getProduct();
                handler.sendMessage(message2);

                //处理斑马发现等数据
                Message message3 = handler.obtainMessage();
                message3.what=ADD_BANMA_FIND_ITEM;
                message3.obj = homePageData.getArticle_list();
                handler.sendMessage(message3);
            }
        }
    }

    private void addProductList(List<ProductItemData> productItemDataList){
        for(int i = 0;i<productItemDataList.size();i++){
            //创建布局
            LinearLayout linearLayout1 = new LinearLayout(getContext());
            linearLayout1.setOrientation(LinearLayout.VERTICAL);
            TextView textView1 = new TextView(getContext());
            textView1.setTextSize(18);
            textView1.setTextColor(getResources().getColor(R.color.text_light));
            ViewGroup.LayoutParams tvlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            ((LinearLayout.LayoutParams) tvlp).setMargins(0, Tool.getPixelsFromDp(getActivity(),40),0,0);
            textView1.setText(productItemDataList.get(i).getTitle());
            textView1.setLayoutParams(tvlp);
            linearLayout1.addView(textView1);

            for(int j = 0;j<productItemDataList.get(i).getList().size();j++){

                View view1 = inflater.inflate(R.layout.ele_product_default,null);
                //添加事件监听
                view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Log.i("xiaobaicai","点击了product第"+jTemp+"列表中的第:"+kTemp+"个");
                        //Toast.makeText(getActivity(),"点击了product第"+jTemp+"列表中的第:"+kTemp+"个",Toast.LENGTH_SHORT).show();
                    }
                });
                /**
                 * 处理数据
                 */
                //先获取view1中的元素
                RelativeLayout rlContainer = view1.findViewById(R.id.product_rl_container);
                ImageView imageView = view1.findViewById(R.id.product_iv);
                TextView tipTag = view1.findViewById(R.id.product_tv_tag);
                TextView tvTitle = view1.findViewById(R.id.product_tv_title);
                TextView tvSubTitle = view1.findViewById(R.id.product_tv_subtitle);
                LinearLayout llTagContainer = view1.findViewById(R.id.ll_tag_container);
                TextView tvTagBlack1 = view1.findViewById(R.id.product_tv_tag_out_black_1);
                TextView tvTagBlack2 = view1.findViewById(R.id.product_tv_tag_out_black_2);
                TextView tvTagRed1 = view1.findViewById(R.id.product_tv_tag_out_regular_1);
                TextView tvPrice = view1.findViewById(R.id.product_tv_price);


                //根据数据对元素进行处理
                ProductListItemData productItemElemData = productItemDataList.get(i).getList().get(j);

                //imageView.setImageBitmap(productItemElemData.getImg());
                Glide.with(getContext()).load(productItemElemData.getImg()).into(imageView);
                if (productItemElemData.getTip_mark() != null && productItemElemData.getTip_mark().length() > 0) {
                    tipTag.setText(productItemElemData.getTip_mark());
                } else {
                    rlContainer.removeView(tipTag);
                }
                tvTitle.setText(productItemElemData.getTitle());
                tvSubTitle.setText(productItemElemData.getSubtitle());
                tvPrice.setText(productItemElemData.getPrice());
                List<ProductMarkItem> markItems = productItemElemData.getMark();
                // List<Boolean> markIsUsed = new ArrayList<Boolean>();
                Boolean[] markIsUsedList = new Boolean[]{false, false, false};
                for (int n = 0; n < markItems.size(); n++) {
                    if (markItems.get(n).getFlag() == 0) {
                        if (!markIsUsedList[0]) {
                            tvTagBlack1.setText(markItems.get(n).getName());
                            markIsUsedList[0] = true;
                        } else if (!markIsUsedList[1]) {
                            tvTagBlack2.setText(markItems.get(n).getName());
                            markIsUsedList[1] = true;
                        }
                    } else {
                        if (!markIsUsedList[2]) {
                            tvTagRed1.setText(markItems.get(n).getName());
                            markIsUsedList[2] = true;
                        }
                    }
                }
                for (int n = 0; n < markIsUsedList.length; n++) {
                    if (!markIsUsedList[n]) {
                        if (n == 0) {
                            llTagContainer.removeView(tvTagBlack1);
                        } else if (n == 1) {
                            llTagContainer.removeView(tvTagBlack2);
                        } else if (n == 2) {
                            llTagContainer.removeView(tvTagRed1);
                        }
                    }
                }

                linearLayout1.addView(view1);
            }
            llElemListViewContainer.addView(linearLayout1);
        }
    }

    private class getHomePageDataBefore{
        private int platform;

        public getHomePageDataBefore(int platform){
            this.platform = platform;
        }

        public int getPlatform() {
            return platform;
        }

        public void setPlatform(int platform) {
            this.platform = platform;
        }
    }
}