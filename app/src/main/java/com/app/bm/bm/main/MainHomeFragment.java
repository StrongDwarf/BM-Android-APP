package com.app.bm.bm.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.app.bm.bm.R;
import com.app.bm.bm.common.adapter.IndexGridAdapter;
import com.app.bm.bm.common.adapter.ProductListAdapter;
import com.app.bm.bm.common.apiUrls.ApiUrl;
import com.app.bm.bm.common.datas.AjaxReceiveData;
import com.app.bm.bm.common.datas.EmptyData;
import com.app.bm.bm.common.datas.HomeActivityItemData;
import com.app.bm.bm.common.datas.HomeArticleListItemData;
import com.app.bm.bm.common.datas.HomeBannerData;
import com.app.bm.bm.common.datas.HomeBannerItemData;
import com.app.bm.bm.common.datas.HomePageData;
import com.app.bm.bm.common.datas.ProductItemData;
import com.app.bm.bm.common.datas.ProductItemElemData;
import com.app.bm.bm.common.datas.ProductListItemData;
import com.app.bm.bm.common.datas.ProductMarkItem;
import com.app.bm.bm.common.debuger.UtilLog;
import com.app.bm.bm.common.extend.AppCompatActivityWithNetWork;
import com.app.bm.bm.common.extend.elem.ElemGridView;
import com.app.bm.bm.common.extend.elem.ElemListView;
import com.app.bm.bm.common.items.HomeBannerItem;
import com.app.bm.bm.common.items.IndexInterestItem;
import com.app.bm.bm.common.tools.Ajax;
import com.app.bm.bm.common.tools.SDCardTools;
import com.app.bm.bm.home.HomeBannerFragment;
import com.app.bm.bm.personal.PersonalUserintrDefaultFragment;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
/*
import com.app.bm.bm.adapter.IndexGridAdapter;
import com.app.bm.bm.entity.IndexInterestItem;
import com.app.bm.bm.fragment.index.IndexBannerFragment;

*/
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


/**
 * 首页fragment
 * create by ### on 2019-1-17
 *
 * 写的太烂了,不是xiaobaicai写的,xiaobaicai也不知道是谁写的
 */
public class MainHomeFragment extends Fragment {

    private ElemGridView indexGvIntexest;               //兴趣主题中的GridView页面
    private IndexGridAdapter indexGridAdapter;
    private List<IndexInterestItem> indexInterestItems;
    private Toolbar toolbar;                        //顶部导航条
    private LinearLayout lastedActivityLinearLayout;            //近期活动

    private HomePageData homePageData;              //首页数据
    private List<HomeBannerItem> homeBannerItemList;        //首页Banner数据
    private LinearLayout llElemListViewContainer;           //首页,限定甄选父容器
    private LinearLayout banmaFindContainer;                //斑马发现父容器

    private List<OneDimenImageView> twoDimenImageView;      //限定甄选,二维ImageView数组

    private HomeBannerFragment homeBannerFragment;

    private List<ProductListData> productListDataList;    //产品列表二维数组

    public static final int UPDATE_BANNER=1;                //更新BANNER
    public static final int UPDATE_BANNER_DATA = 2;         //更新BANNER数据
    public static final int UPDATE_BANNER_ITEM_BITMAP = 3;          //更新BANNER item
    public static final int ADD_ACTIVITY_ITEM = 4;          //添加近期活动子元素
    public static final int ADD_PRODUCT_LIST = 5;           //添加产品列表
    public static final int UPDATE_PRODUCT_LIST_BITMAP = 6;    //更新产品列表中的图片,图片加载完成后执行
    public static final int ADD_BANMA_FIND_ITEM = 7;            //添加斑马发现子元素


    private LayoutInflater inflater1;



    private Handler UIhandler = new Handler(){

        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case UPDATE_BANNER:
                    Log.i("xiaobaicai","更新Banner");
                    homeBannerFragment =new HomeBannerFragment((List<HomeBannerItem>)msg.obj);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fra_index_banner,homeBannerFragment).commit();
                    break;
                case UPDATE_BANNER_ITEM_BITMAP:
                    Log.i("xiaobaicai","更新Banner Item bitmap");
                    int i = ((UpdateBannerData) msg.obj).getI();
                    Log.i("xiaobaicai","更新：-->"+i);
                    Bitmap bitmap = ((UpdateBannerData) msg.obj).getBitmap();
                    homeBannerFragment.updateBannerImg(i,bitmap);
                    homeBannerItemList.get(i).setImgData(bitmap);
                    break;
                case ADD_ACTIVITY_ITEM:
                    LinearLayout linearLayout = getLastedActivityItemLinearLayout(((AddLastedActivityData) msg.obj).getText(),((AddLastedActivityData) msg.obj).getBitmap(),((AddLastedActivityData) msg.obj).getUrl());
                    lastedActivityLinearLayout.addView(linearLayout,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    break;
                case ADD_PRODUCT_LIST:
                    for(int j = 0;j<productListDataList.size();j++){

                        //创建布局
                        LinearLayout linearLayout1 = new LinearLayout(getContext());
                        linearLayout1.setOrientation(LinearLayout.VERTICAL);
                        TextView textView1 = new TextView(getContext());
                        textView1.setTextSize(18);
                        textView1.setTextColor(getResources().getColor(R.color.text_light));
                        ViewGroup.LayoutParams tvlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        ((LinearLayout.LayoutParams) tvlp).setMargins(0,getPixelsFromDp(40),0,0);
                        textView1.setText(productListDataList.get(j).getTitle());
                        textView1.setLayoutParams(tvlp);
                        linearLayout1.addView(textView1);

                        //新建一个一维ImageView数组
                        OneDimenImageView oneDimenImageView = new OneDimenImageView();
                        for(int k = 0;k<productListDataList.get(j).getProductItemElemDataList().size();k++){
                            final int jTemp = j;
                            final int kTemp = k;
                            View view1 = inflater1.inflate(R.layout.ele_product_default,null);
                            //添加事件监听
                            view1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //Log.i("xiaobaicai","点击了product第"+jTemp+"列表中的第:"+kTemp+"个");
                                    Toast.makeText(getActivity(),"点击了product第"+jTemp+"列表中的第:"+kTemp+"个",Toast.LENGTH_SHORT).show();
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

                            //将imageView插入到一维ImageView数组中
                            oneDimenImageView.getOnedimenImageView().add(imageView);

                            //根据数据对元素进行处理
                            ProductItemElemData productItemElemData = productListDataList.get(j).getProductItemElemDataList().get(k);

                            //imageView.setImageBitmap(productItemElemData.getImg());
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
                        //将一维数组插入到二维数组中,
                        twoDimenImageView.add(oneDimenImageView);
                        //将产品列表加入到页面容器中
                        llElemListViewContainer.addView(linearLayout1);
                    }
                    break;
                case UPDATE_PRODUCT_LIST_BITMAP:
                    UpdateProductData obj = (UpdateProductData) msg.obj;
                    Log.i("xiaobaicai","更新图片:i:"+obj.getI()+"j:"+obj.getJ());
                    if(twoDimenImageView.get(obj.getI()).getOnedimenImageView().get(obj.getJ()) != null){
                        twoDimenImageView.get(obj.getI()).getOnedimenImageView().get(obj.getJ()).setImageBitmap(obj.getBitmap());
                        Log.i("xiaobaicai","更新完成");
                    }
                    break;
                case ADD_BANMA_FIND_ITEM:
                    LinearLayout linearLayout1 = getBannerFindItemLinearLayout(((AddBanmaFindData) msg.obj).getText(),((AddBanmaFindData) msg.obj).getBitmap(),((AddBanmaFindData) msg.obj).getUrl());
                    banmaFindContainer.addView(linearLayout1);
                    break;

            }
        }
    };

    public LinearLayout getBannerFindItemLinearLayout(String text,Bitmap bitmap,final String url){
        LinearLayout linearLayout = new LinearLayout(getContext());
        ImageView imageView = new ImageView(getContext());
        TextView textView = new TextView(getContext());

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams lllp = new LinearLayout.LayoutParams(getPixelsFromDp(190),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ((LinearLayout.LayoutParams) lllp).setMargins(0,0,getPixelsFromDp(20),0);
        linearLayout.setLayoutParams(lllp);

        imageView.setImageBitmap(bitmap);
        //imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ViewGroup.LayoutParams ivlp = new LinearLayout.LayoutParams(getPixelsFromDp(190),
                getPixelsFromDp(110));
        imageView.setLayoutParams(ivlp);

        textView.setText(text);
        textView.setTextSize(12);
        textView.setLineSpacing(getPixelsFromDp(5),1);
        ViewGroup.LayoutParams tvlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(tvlp);

        linearLayout.addView(imageView);
        linearLayout.addView(textView);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"点击了斑马发现+"+url,Toast.LENGTH_SHORT).show();
            }
        });

        return linearLayout;
    }

    public LinearLayout getLastedActivityItemLinearLayout(String text,Bitmap bitmap,final String url){
        LinearLayout linearLayout = new LinearLayout(getContext());
        ImageView imageView = new ImageView(getContext());
        TextView textView = new TextView(getContext());

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(getPixelsFromDp(5),getPixelsFromDp(0),getPixelsFromDp(5),getPixelsFromDp(5));

        imageView.setImageBitmap(bitmap);
        imageView.setAdjustViewBounds(true);

        textView.setTextColor(getResources().getColor(R.color.text_light));
        textView.setTextSize(14);
        textView.setText(text);
        textView.setPadding(getPixelsFromDp(0),getPixelsFromDp(5),getPixelsFromDp(0),getPixelsFromDp(0));

        linearLayout.addView(imageView,new LinearLayout.LayoutParams(getPixelsFromDp(220),
                ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(textView,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("xiaobaicai","点击了近期活动="+url);
            }
        });

        return linearLayout;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate){
        View rootView = inflater.inflate(R.layout.main_home_fragment,null);


        //设置Banner页面
        //初始化数据

        lastedActivityLinearLayout = rootView.findViewById(R.id.lasted_activity);
        lastedActivityLinearLayout.removeAllViews();
        llElemListViewContainer = rootView.findViewById(R.id.ll_elemlistview_container);
        llElemListViewContainer.removeAllViews();

        banmaFindContainer = rootView.findViewById(R.id.home_find_container);
        banmaFindContainer.removeAllViews();

        inflater1 = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        twoDimenImageView = new ArrayList<OneDimenImageView>();

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.i("xiaobaicai","获取首页数据");
                AjaxReceiveData homePageAfterData = Ajax.get(new getHomePageDataBefore(1),ApiUrl.getHomePage());
                if(homePageAfterData != null){
                    homePageData = (new Gson()).fromJson((new Gson()).toJson(((AjaxReceiveData) homePageAfterData).getData()),HomePageData.class);

                    //String str = "{'code':0,'message':'','data':{'banner':{'isMain':1,'list':[{'img':'https:\\/\\/product-ssl-qiniu.bmtrip.com\\/product_5c1a19e728054.png'}]}}}";
                    //HomePageData homePageData = (new Gson()).fromJson(str,HomePageData.class);

                    //Log.i("xiaobaicai","Banner:-->"+homePageData.getBanner().toString());
                    //Log.i("xiaobaicai","Theme_list:-->"+homePageData.getTheme_list().toString());
                    //Log.i("xiaobaicai","activity:-->"+homePageData.getActivity().toString());
                    Log.i("xiaobaicai","Product:-->"+homePageData.getProduct().toString());
                    //Log.i("xiaobaicai","acticle_list:-->"+homePageData.getArticle_list().toString());

                    //处理Banner

                    //处理限定甄选
                    //处理斑马发现

                    //处理Banner
                    homeBannerItemList = new ArrayList<HomeBannerItem>();
                    int bannerListLength = homePageData.getBanner().getList().size();

                    Bitmap defaultBannerBitmap = BitmapFactory.decodeResource(getContext().getResources(),R.mipmap.banner1); //banner default图片
                    for(int i =0;i<bannerListLength;i++){
                        String title = ((HomeBannerItemData) homePageData.getBanner().getItem(i)).getTitle();
                        String text = ((HomeBannerItemData) homePageData.getBanner().getItem(i)).getSubtitle();
                        String url = ((HomeBannerItemData) homePageData.getBanner().getItem(i)).getUrl();
                        homeBannerItemList.add(new HomeBannerItem((i+1)+"",defaultBannerBitmap,title,text,url));
                    }
                    Message message = UIhandler.obtainMessage();
                    message.what=UPDATE_BANNER;
                    message.obj = homeBannerItemList;
                    UIhandler.sendMessage(message);

                    //获取图片
                    for(int i = 0;i<bannerListLength;i++){
                        String imgUrl = ((HomeBannerItemData) homePageData.getBanner().getItem(i)).getImg().toString();
                        (new getBannerBitmapThread(i,imgUrl)).start();
                    }


                    //处理近期活动
                    for(int i = 0;i< homePageData.getActivity().getList().size();i++){
                        HomeActivityItemData homeActivityItemData = homePageData.getActivity().getList().get(i);
                        String text = homeActivityItemData.getTitle();
                        String url = homeActivityItemData.getUrl();
                        String img = homeActivityItemData.getImg();
                        (new getLastedActivityBitmapThread(url,text,img)).start();
                    }

                    //处理限定甄选等列表数据
                    productListDataList = new ArrayList<ProductListData>();
                    for(int i =0;i< homePageData.getProduct().size();i++){
                        List<ProductItemElemData> productItemElemDataList =  new ArrayList<ProductItemElemData>();
                        for(int j = 0;j<homePageData.getProduct().get(i).getList().size();j++){
                            ProductListItemData productListItemData = homePageData.getProduct().get(i).getList().get(j);
                            productItemElemDataList.add(new ProductItemElemData(productListItemData.getId(),productListItemData.getTitle(),productListItemData.getSubtitle(),productListItemData.getPrice(),productListItemData.getUrl(),productListItemData.getTip_mark(),productListItemData.getMark()));
                        }
                        //先取出数据,将其存放在全局变量中,此时数据为空数据
                        ProductListData productListData = new ProductListData(((ProductItemData) homePageData.getProduct().get(i)).getTitle(),productItemElemDataList);
                        productListDataList.add(productListData);
                        //此时productListDataList中的数据中的bitmap都是null;
                    }
                    //发送添加productlist信号
                    Message message1 = UIhandler.obtainMessage();
                    message1.what=ADD_PRODUCT_LIST;
                    UIhandler.sendMessage(message1);



                    //开启新线程获取限定甄选图片数据图片数据
                    for(int i =0;i<homePageData.getProduct().size();i++){
                        for(int j = 0;j<homePageData.getProduct().get(i).getList().size();j++){
                            (new getProductItemBitmapThread(homePageData.getProduct().get(i).getList().get(j),i,j)).start();
                        }
                    }

                    //处理斑马发现
                    for(int i = 0;i< homePageData.getArticle_list().getList().size();i++){
                        HomeArticleListItemData homeArticleListItemData = homePageData.getArticle_list().getList().get(i);
                        String text = homeArticleListItemData.getTitle();
                        String url = homeArticleListItemData.getUrl();
                        String img = homeArticleListItemData.getImg();
                        (new getBanmaFindBitmapThread(url,text,img)).start();
                    }

                    //Log.i("xiaobaicai","banner_list:"+homePageData.getBanner().getList().toString());
                }
            }
        }).start();

       // Fragment fragment =new HomeBannerFragment(homeBannerItemList);
       // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fra_index_banner,fragment).commit();

        //获取兴趣元素
        indexGvIntexest = rootView.findViewById(R.id.index_gv_interst_theme);
        List<IndexInterestItem> indexInterestItems = new ArrayList<IndexInterestItem>();
        int []InterestImg = new int[]{R.drawable.ic_interest1,R.drawable.ic_interest2,R.drawable.ic_interest3,R.drawable.ic_interest4,
                R.drawable.ic_interest5,R.drawable.ic_interest6,R.drawable.ic_interest7,R.drawable.ic_interest8};
        String []InerestTitle = new String[]{"浪漫蜜月","家庭亲子","海岛海滨","异域风情","自然奇观","奇趣探险","异域风情","奇趣探险"};
        String []InterestId = new String[]{"95","114","90","91","92","89","92","89","94","112"};
        for(int i = 0;i<8;i++){
            indexInterestItems.add(new IndexInterestItem(InterestId[i],InterestImg[i],InerestTitle[i],"空url"));
        }
        IndexGridAdapter indexGridAdapter = new IndexGridAdapter(getActivity(),indexInterestItems);
        indexGvIntexest.setAdapter(indexGridAdapter);

        //初始化indexInterestItems数据

        //设置toolbar
        toolbar = rootView.findViewById(R.id.toolbar);

        toolbar.inflateMenu(R.menu.bm_toolbar_menu_search);
        toolbar.setNavigationIcon(R.drawable.ic_servicer);
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
        toolbar.setNavigationOnClickListener(onClickListener);


        return rootView;
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.iv_search:
                    Log.i("xiaobaicai","点击了搜索");
                    break;
            }
        }
    };

    public Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Log.i("xiaobaicai","点击了toolbar上的菜单");
            return true;
        }
    };


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

    //获取Banner图片线程
    private class getBannerBitmapThread extends Thread{
        private int i;
        private String imgUrl;

        public getBannerBitmapThread(int i,String imgUrl){
            this.i = i;
            this.imgUrl = imgUrl;
        }


        @Override
        public void run(){
            Uri uri = Uri.parse(imgUrl);                           //转化为url
            String path = uri.getPath();                        //获取url中的path
            String[] temp = path.split("/");
            final String dirPath = getContext().getExternalCacheDir().getAbsolutePath()+ getDirPath(path);//+ File.separator +temp[temp.length - 1];    //获取文件的本地位置
            final String fileName = temp[temp.length - 1];
            File file = new File(dirPath);
            if(!file.exists()){
                file.mkdirs(); //递归创建自定义目录
            }
            final File bitFile = new File(dirPath,fileName);
            if(bitFile.exists()) {
                Bitmap bitmap =  SDCardTools.loadBitmapFromSDCard(dirPath+File.separator+fileName);
                Message message = UIhandler.obtainMessage();
                message.what= UPDATE_BANNER_ITEM_BITMAP;
                message.obj = new UpdateBannerData(i,bitmap);
                UIhandler.sendMessage(message);
            }else{
                OkHttpUtils.get().url(imgUrl).tag(this)
                        .build()
                        .connTimeOut(20000).readTimeOut(20000).writeTimeOut(20000)
                        .execute(new BitmapCallback() {
                            @Override
                            public void onError(okhttp3.Call call, Exception e, int id) {
                                Log.e("xiaobaicai", e.toString());
                            }
                            @Override
                            public void onResponse(final Bitmap response, int id) {
                                SDCardTools.saveBitmapToSDCardDir(response, dirPath, fileName, getActivity().getBaseContext());

                                Message message = UIhandler.obtainMessage();
                                message.what= UPDATE_BANNER_ITEM_BITMAP;
                                message.obj = new UpdateBannerData(i,response);
                                UIhandler.sendMessage(message);
                            }
                        });
            }
        }
    }

    //获取限定甄选子元素图片线程
    private class getProductItemBitmapThread extends Thread{
        private ProductListItemData productListItemData;
        private int i;
        private int j;

        public getProductItemBitmapThread(ProductListItemData productListItemData,int i,int j){
            this.productListItemData = productListItemData;
            this.i = i;
            this.j = j;
        }

        @Override
        public void run(){
            Uri uri = Uri.parse(productListItemData.getImg());                           //转化为url
            String path = uri.getPath();                        //获取url中的path
            String[] temp = path.split("/");
            final String dirPath = getContext().getExternalCacheDir().getAbsolutePath()+ getDirPath(path);//+ File.separator +temp[temp.length - 1];    //获取文件的本地位置
            final String fileName = temp[temp.length - 1];
            File file = new File(dirPath);
            if(!file.exists()){
                file.mkdirs(); //递归创建自定义目录
            }
            final File bitFile = new File(dirPath,fileName);
            if(bitFile.exists()) {
                Bitmap bitmap =  SDCardTools.loadBitmapFromSDCard(dirPath+File.separator+fileName);
                // ProductItemElemData productItemElemData = new ProductItemElemData(productListItemData.getId(),bitmap,productListItemData.getTitle(), productListItemData.getSubtitle(),productListItemData.getPrice(),productListItemData.getUrl(),productListItemData.getTip_mark(),productListItemData.getMark());
                //productListDataList.get(i).getProductItemElemDataList().get(j).setImg(bitmap);

                Message message = UIhandler.obtainMessage();
                message.what=UPDATE_PRODUCT_LIST_BITMAP;
                message.obj = new UpdateProductData(i,j,bitmap);
                UIhandler.sendMessage(message);
            }else{
                OkHttpUtils.get().url(productListItemData.getImg()).tag(this)
                        .build()
                        .connTimeOut(20000).readTimeOut(20000).writeTimeOut(20000)
                        .execute(new BitmapCallback() {
                            @Override
                            public void onError(okhttp3.Call call, Exception e, int id) {
                                Log.e("xiaobaicai", e.toString());
                            }
                            @Override
                            public void onResponse(final Bitmap response, int id) {
                                SDCardTools.saveBitmapToSDCardDir(response, dirPath, fileName, getActivity().getBaseContext());
                                //ProductItemElemData productItemElemData = new ProductItemElemData(productListItemData.getId(),response,productListItemData.getTitle(), productListItemData.getSubtitle(),productListItemData.getPrice(),productListItemData.getUrl(),productListItemData.getTip_mark(),productListItemData.getMark());
                                //productListDataList.get(i).getProductItemElemDataList().get(j).setImg(response);

                                Message message = UIhandler.obtainMessage();
                                message.what=UPDATE_PRODUCT_LIST_BITMAP;
                                message.obj = new UpdateProductData(i,j,response);
                                UIhandler.sendMessage(message);
                            }
                        });
            }
        }
    }

    //获取斑马发现图片线程
    private class getBanmaFindBitmapThread extends Thread{
        private String url;
        private String text;
        private String img;

        public getBanmaFindBitmapThread(String url,String text,String img){
            this.url = url;
            this.text = text;
            this.img = img;
        }

        @Override
        public void run(){
            Uri uri = Uri.parse(img);                           //转化为url
            String path = uri.getPath();                        //获取url中的path
            String[] temp = path.split("/");
            final String dirPath = getContext().getExternalCacheDir().getAbsolutePath()+ getDirPath(path);//+ File.separator +temp[temp.length - 1];    //获取文件的本地位置
            final String fileName = temp[temp.length - 1];
            File file = new File(dirPath);
            if(!file.exists()){
                file.mkdirs(); //递归创建自定义目录
            }
            final File bitFile = new File(dirPath,fileName);
            if(bitFile.exists()) {
                Bitmap bitmap =  SDCardTools.loadBitmapFromSDCard(dirPath+File.separator+fileName);
                Message message = UIhandler.obtainMessage();
                message.what= ADD_BANMA_FIND_ITEM;
                message.obj = new AddBanmaFindData(url,text,bitmap);
                UIhandler.sendMessage(message);
            }else{
                OkHttpUtils.get().url(img).tag(this)
                        .build()
                        .connTimeOut(20000).readTimeOut(20000).writeTimeOut(20000)
                        .execute(new BitmapCallback() {
                            @Override
                            public void onError(okhttp3.Call call, Exception e, int id) {
                                Log.e("xiaobaicai", e.toString());
                            }
                            @Override
                            public void onResponse(final Bitmap response, int id) {
                                SDCardTools.saveBitmapToSDCardDir(response, dirPath, fileName, getActivity().getBaseContext());

                                Message message = UIhandler.obtainMessage();
                                message.what= ADD_BANMA_FIND_ITEM;
                                message.obj = new AddBanmaFindData(url,text,response);
                                UIhandler.sendMessage(message);
                            }
                        });
            }
        }
    }

    //获取近期活动图片线程
    private class getLastedActivityBitmapThread extends Thread{
        private String url;
        private String text;
        private String img;

        public getLastedActivityBitmapThread(String url,String text,String img){
            this.url = url;
            this.text = text;
            this.img = img;
        }

        @Override
        public void run(){
            Uri uri = Uri.parse(img);                           //转化为url
            String path = uri.getPath();                        //获取url中的path
            String[] temp = path.split("/");
            final String dirPath = getContext().getExternalCacheDir().getAbsolutePath()+ getDirPath(path);//+ File.separator +temp[temp.length - 1];    //获取文件的本地位置
            final String fileName = temp[temp.length - 1];
            File file = new File(dirPath);
            if(!file.exists()){
                file.mkdirs(); //递归创建自定义目录
            }
            final File bitFile = new File(dirPath,fileName);
            if(bitFile.exists()) {
                Bitmap bitmap =  SDCardTools.loadBitmapFromSDCard(dirPath+File.separator+fileName);
                Message message = UIhandler.obtainMessage();
                message.what= ADD_ACTIVITY_ITEM;
                message.obj = new AddLastedActivityData(url,text,bitmap);
                UIhandler.sendMessage(message);
            }else{
                OkHttpUtils.get().url(img).tag(this)
                        .build()
                        .connTimeOut(20000).readTimeOut(20000).writeTimeOut(20000)
                        .execute(new BitmapCallback() {
                            @Override
                            public void onError(okhttp3.Call call, Exception e, int id) {
                                Log.e("xiaobaicai", e.toString());
                            }
                            @Override
                            public void onResponse(final Bitmap response, int id) {
                                SDCardTools.saveBitmapToSDCardDir(response, dirPath, fileName, getActivity().getBaseContext());

                                Message message = UIhandler.obtainMessage();
                                message.what= ADD_ACTIVITY_ITEM;
                                message.obj = new AddLastedActivityData(url,text,response);
                                UIhandler.sendMessage(message);
                            }
                        });
            }
        }
    }

    //更新产品列表的数据
    private class UpdateProductData{
        private int i;
        private int j;
        private Bitmap bitmap;
        public UpdateProductData(int i,int j,Bitmap bitmap){
            this.i = i;
            this.j = j;
            this.bitmap = bitmap;
        }

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
        }

        public void setI(int i) {
            this.i = i;
        }

        public void setJ(int j) {
            this.j = j;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }
    }

    //更新Banner的数据
    private class UpdateBannerData{
        private int i;
        private Bitmap bitmap;

        public UpdateBannerData(int i,Bitmap bitmap){
            this.i = i;
            this.bitmap = bitmap;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public int getI() {
            return i;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public void setI(int i) {
            this.i = i;
        }
    }

    //更新斑马发现
    private class AddBanmaFindData{
        private String url;
        private String text;
        private Bitmap bitmap;

        public AddBanmaFindData(){}

        public AddBanmaFindData(String url,String text,Bitmap bitmap){
            this.url = url;
            this.bitmap = bitmap;
            this.text = text;
        }

        public String getUrl() {
            return url;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public String getText() {
            return text;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    //更新添加近期活动.
    private class AddLastedActivityData{
        private String url;
        private String text;
        private Bitmap bitmap;

        public AddLastedActivityData(){}

        public AddLastedActivityData(String url,String text,Bitmap bitmap){
            this.url = url;
            this.bitmap = bitmap;
            this.text = text;
        }

        public String getUrl() {
            return url;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public String getText() {
            return text;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    //添加产品列表数据
    private class ProductListData {
        private String title;
        private List<ProductItemElemData> productItemElemDataList;

        public ProductListData() {
        };

        public ProductListData(String title, List<ProductItemElemData> productItemElemDataList) {
            this.title = title;
            this.productItemElemDataList = productItemElemDataList;
        }

        public String getTitle() {
            return title;
        }

        public List<ProductItemElemData> getProductItemElemDataList() {
            return productItemElemDataList;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setProductItemElemDataList(List<ProductItemElemData> productItemElemDataList) {
            this.productItemElemDataList = productItemElemDataList;
        }

    }


    //ImagerView一维数组存储器
    private class OneDimenImageView{
        private List<ImageView> onedimenImageView;       //内部保存一个一维Bitmap数组
        public OneDimenImageView(){
            this.onedimenImageView = new ArrayList<ImageView>();
        }

        public void setOnedimenImageView(List<ImageView> onedimenImageView) {
            this.onedimenImageView = onedimenImageView;
        }

        public List<ImageView> getOnedimenImageView() {
            return onedimenImageView;
        }
    }

    //输入网络请求中的path字段,输出应该用于映射到本地的path
    private String getDirPath(String path){
        String[] paths = path.trim().split("/");
        if(paths.length <= 2){
            return File.separator+"default";
        }else{
            String str = "";
            for(int i =1;i<paths.length-1;i++){
                str = str+File.separator+paths[i];
            }
            return str;
        }
    }

    //输入dp,输出px
    private int getPixelsFromDp(int size){

        DisplayMetrics metrics =new DisplayMetrics();

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return(size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;

    }
}