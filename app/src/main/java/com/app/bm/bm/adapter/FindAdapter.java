package com.app.bm.bm.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bm.bm.R;
import com.app.bm.bm.entity.FindItem;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * 发现列表适配器
 *
 *@author xiaobaicai
 *@create time 2018-12-11
 **/

public class FindAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_FOOTER = 0;   //设置底部布局
    private static final int TYPE_DEFAULT = 1;  //设置默认布局

    private List<FindItem> mData;               //数据源
    private Context mContext;                   //上下文
    private boolean mShowFooter = true;         //判断是不是最后一个item，默认是true

    //构造函数
    public FindAdapter(Context mContext)
    {
        this.mContext = mContext;
    }

    //设置数据并刷新
    public void setData(List<FindItem> Data)
    {
        this.mData = Data;
        this.notifyDataSetChanged();
    }

    //设置不同的item
    @Override
    public int getItemViewType(int position)
    {
        //判断当前位置+1是不是等于数据总数(因为数据从0开始计数)，是的就加载底部布局刷新，不是就加载默认布局
        if(position + 1 == getItemCount())
        {
            return TYPE_FOOTER;
        }
        else
        {
            return TYPE_DEFAULT;
        }
    }

    //设置布局(相当于设置covertView)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(viewType == TYPE_DEFAULT)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find, parent, false);

            DefaultViewHolder vh = new DefaultViewHolder(v);

            return vh;
        }
        else
        {
            // 实例化布局
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_refresh_footer, null);
            // 代码实现加载布局
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            return new FooterViewHolder(view);
        }
    }

    //在布局中加载数据(绑定数据)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position)
    {
        //用来在运行时指出对象是某一个对象
        if(holder instanceof DefaultViewHolder)
        {
            FindItem findItem = mData.get(position);
            if(findItem == null)
            {
                return;
            }
            ((DefaultViewHolder) holder).text1.setText(findItem.getText1());
            ((DefaultViewHolder) holder).text2.setText(findItem.getText2());
            ((DefaultViewHolder) holder).tag.setText(findItem.getTag());
            /*
            //从网络上获取图片,并且设置为find iten图片
            Bitmap bitmap = getHttpBitmap(findItem.getImgUrl());
            ((DefaultViewHolder) holder).img .setImageBitmap(bitmap);	//设置Bitmap
            */
        }
    }

    /**
     * 从服务器取图片
     * @param url
     * @return bitmap
     */
    public static Bitmap getHttpBitmap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            Log.d(TAG, url);
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //获取数据数目
    @Override
    public int getItemCount()
    {
        //判断是不是显示底部,是就返回1,不是返回0
        int begin = mShowFooter?1:0;
        //没有数据的时候,直接返回begin
        if(mData == null)
        {
            return begin;
        }
        //因为底部布局要占一个位置,所以总数目要+1
        return mData.size() + begin;
    }

    //设置是否显示底部加载提示(加值传递给全局变量)
    public void isShowFooter(boolean mShowFooter){this.mShowFooter = mShowFooter;}

    //判断是否显示底部,数据来自全局变量
    public boolean ismShowFooter(){return this.mShowFooter;}

    //底部布局的ViewHolder
    public class FooterViewHolder extends RecyclerView.ViewHolder
    {
        public FooterViewHolder(View view){
            super(view);
        }
    }

    //默认布局的ViewHolder
    public class DefaultViewHolder extends RecyclerView.ViewHolder
    {
        public TextView text1;
        public TextView text2;
        public ImageView img;
        public TextView tag;

        public DefaultViewHolder(View itemView)
        {
            super(itemView);

            text1 = (TextView) itemView.findViewById(R.id.text1);
            text2 = (TextView) itemView.findViewById(R.id.text2);
            tag = (TextView) itemView.findViewById(R.id.tag);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}