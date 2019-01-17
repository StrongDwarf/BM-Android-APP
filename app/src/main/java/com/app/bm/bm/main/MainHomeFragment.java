package com.app.bm.bm.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.app.bm.bm.R;
import com.app.bm.bm.common.adapter.IndexGridAdapter;
import com.app.bm.bm.common.apiUrls.ApiUrl;
import com.app.bm.bm.common.datas.AjaxReceiveData;
import com.app.bm.bm.common.datas.EmptyData;
import com.app.bm.bm.common.datas.HomeActivityItemData;
import com.app.bm.bm.common.datas.HomeBannerData;
import com.app.bm.bm.common.datas.HomeBannerItemData;
import com.app.bm.bm.common.datas.HomePageData;
import com.app.bm.bm.common.debuger.UtilLog;
import com.app.bm.bm.common.extend.AppCompatActivityWithNetWork;
import com.app.bm.bm.common.extend.elem.ElemGridView;
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
import android.widget.TextView;
/*
import com.app.bm.bm.adapter.IndexGridAdapter;
import com.app.bm.bm.entity.IndexInterestItem;
import com.app.bm.bm.fragment.index.IndexBannerFragment;

*/
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainHomeFragment extends Fragment {

    private ElemGridView indexGvIntexest;               //兴趣主题中的GridView页面
    private IndexGridAdapter indexGridAdapter;
    private List<IndexInterestItem> indexInterestItems;
    private Toolbar toolbar;                        //顶部导航条
    private LinearLayout lastedActivityLinearLayout;            //近期活动

    private HomePageData homePageData;              //首页数据
    private List<HomeBannerItem> homeBannerItemList;        //首页Banner数据

    private HomeBannerFragment homeBannerFragment;

    public static final int UPDATE_BANNER=1;
    public static final int UPDATE_BANNER_DATA = 2;
    public static final int UPDATE_BANNER_ITEM_BITMAP = 3;
    public static final int ADD_ACTIVITY_ITEM = 4;

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

            }
        }
    };

    private Handler DATAhandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == UPDATE_BANNER_DATA){
                Log.i("xiaobaicai","更新Banner数据");
                //((HomeBannerItem) msg.obj).getId()
            }
        }
    };

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
                    //Log.i("xiaobaicai","Product:-->"+homePageData.getProduct().toString());
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

    //获得近期活动 LinearLayout
    private LinearLayout getLastedActivityLinearLayoutItem(){
        return new LinearLayout(getContext());
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