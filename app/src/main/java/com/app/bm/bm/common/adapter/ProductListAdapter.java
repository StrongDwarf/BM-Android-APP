package com.app.bm.bm.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.bm.bm.R;
import com.app.bm.bm.common.datas.ProductItemElemData;
import com.app.bm.bm.common.datas.ProductListItemData;
import com.app.bm.bm.common.datas.ProductMarkItem;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ProductItemElemData> data;
    private LayoutInflater inflater;

    public ProductListAdapter(Context context,List<ProductItemElemData> data){
        inflater=LayoutInflater.from(context);
        this.data = data;
    }

    //创建没一行的view用RecyclerView.ViewHolder包装
    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        Log.i("xiaobaicai","创建元素");
        View itemView = inflater.inflate(R.layout.ele_product_default,null);
        return new ViewHolder(itemView);
    }

    //给每一行View填充数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,int position){
        if(data.get(position).getImg() != null){
            ((ViewHolder)viewHolder).imageView.setImageBitmap(data.get(position).getImg());
        }
            if (data.get(position).getTip_mark() != null && data.get(position).getTip_mark().length() > 0) {
                ((ViewHolder)viewHolder).tipTag.setText(data.get(position).getTip_mark());
            } else {
                ((ViewHolder)viewHolder).rlContainer.removeView(((ViewHolder)viewHolder).tipTag);
            }
        ((ViewHolder)viewHolder).tvTitle.setText(data.get(position).getTitle());
        ((ViewHolder)viewHolder).tvSubTitle.setText(data.get(position).getSubtitle());
        ((ViewHolder)viewHolder).tvPrice.setText(data.get(position).getPrice());
            List<ProductMarkItem> markItems = data.get(position).getMark();
            // List<Boolean> markIsUsed = new ArrayList<Boolean>();
            Boolean[] markIsUsedList = new Boolean[]{false, false, false};
            for (int i = 0; i < markItems.size(); i++) {
                if (markItems.get(i).getFlag() == 0) {
                    if (!markIsUsedList[0]) {
                        ((ViewHolder)viewHolder).tvTagBlack1.setText(markItems.get(i).getName());
                        markIsUsedList[0] = true;
                    } else if (!markIsUsedList[1]) {
                        ((ViewHolder)viewHolder).tvTagBlack2.setText(markItems.get(i).getName());
                        markIsUsedList[1] = true;
                    }
                } else {
                    if (!markIsUsedList[2]) {
                        ((ViewHolder)viewHolder).tvTagRed1.setText(markItems.get(i).getName());
                        markIsUsedList[2] = true;
                    }
                }
            }
            for (int i = 0; i < markIsUsedList.length; i++) {
                if (!markIsUsedList[i]) {
                    if (i == 0) {
                        ((ViewHolder)viewHolder).llTagContainer.removeView(((ViewHolder)viewHolder).tvTagBlack1);
                    } else if (i == 1) {
                        ((ViewHolder)viewHolder).llTagContainer.removeView(((ViewHolder)viewHolder).tvTagBlack2);
                    } else if (i == 2) {
                        ((ViewHolder)viewHolder).llTagContainer.removeView(((ViewHolder)viewHolder).tvTagRed1);
                    }
                }
            }
    }

    //数据源的数量
    @Override
    public int getItemCount(){
        return data.size();
    }

    /* 获取每一行的View
    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            //xml文件加载成View
            convertView = inflater.inflate(R.layout.ele_product_default,parent,false);
            viewHolder.rlContainer = convertView.findViewById(R.id.product_rl_container);
            viewHolder.imageView = convertView.findViewById(R.id.product_iv);
            viewHolder.tipTag = convertView.findViewById(R.id.product_tv_tag);
            viewHolder.tvTitle = convertView.findViewById(R.id.product_tv_title);
            viewHolder.tvSubTitle = convertView.findViewById(R.id.product_tv_subtitle);
            viewHolder.llTagContainer = convertView.findViewById(R.id.ll_tag_container);
            viewHolder.tvTagBlack1 = convertView.findViewById(R.id.product_tv_tag_out_black_1);
            viewHolder.tvTagBlack2 = convertView.findViewById(R.id.product_tv_tag_out_black_2);
            viewHolder.tvTagRed1 = convertView.findViewById(R.id.product_tv_tag_out_regular_1);
            viewHolder.tvPrice = convertView.findViewById(R.id.product_tv_price);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //使用数据进行渲染
        if(data.get(position) != null) {
            viewHolder.imageView.setImageBitmap(data.get(position).getImg());
            if (data.get(position).getTip_mark() != null && data.get(position).getTip_mark().length() > 0) {
                viewHolder.tipTag.setText(data.get(position).getTip_mark());
            } else {
                viewHolder.rlContainer.removeView(viewHolder.tipTag);
            }
            viewHolder.tvTitle.setText(data.get(position).getTitle());
            viewHolder.tvSubTitle.setText(data.get(position).getSubtitle());
            viewHolder.tvPrice.setText(data.get(position).getPrice());
            List<ProductMarkItem> markItems = data.get(position).getMark();
            // List<Boolean> markIsUsed = new ArrayList<Boolean>();
            Boolean[] markIsUsedList = new Boolean[]{false, false, false};
            for (int i = 0; i < markItems.size(); i++) {
                if (markItems.get(i).getFlag() == 0) {
                    if (!markIsUsedList[0]) {
                        viewHolder.tvTagBlack1.setText(markItems.get(i).getName());
                        markIsUsedList[0] = true;
                    } else if (!markIsUsedList[1]) {
                        viewHolder.tvTagBlack2.setText(markItems.get(i).getName());
                        markIsUsedList[1] = true;
                    }
                } else {
                    if (!markIsUsedList[2]) {
                        viewHolder.tvTagRed1.setText(markItems.get(i).getName());
                        markIsUsedList[2] = true;
                    }
                }
            }
            for (int i = 0; i < markIsUsedList.length; i++) {
                if (!markIsUsedList[i]) {
                    if (i == 0) {
                        viewHolder.llTagContainer.removeView(viewHolder.tvTagBlack1);
                    } else if (i == 1) {
                        viewHolder.llTagContainer.removeView(viewHolder.tvTagBlack2);
                    } else if (i == 2) {
                        viewHolder.llTagContainer.removeView(viewHolder.tvTagRed1);
                    }
                }
            }
        }

        //设置监听
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("xiaobaicai","点击了产品页"+" url:"+data.get(position).getUrl()+"  id:"+data.get(position).getId());
            }
        });

        return convertView;
    }
    */



    public class ViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout rlContainer;     //产品图片容器
        private ImageView imageView;        //产品图片
        private TextView tipTag;            //产品图片中的tag
        private TextView tvTitle;           //产品title
        private TextView tvSubTitle;        //产品subtitle
        private LinearLayout llTagContainer;    //产品tag容器
        private TextView tvTagBlack1;           //黑色的标签1
        private TextView tvTagBlack2;           //黑色的标签2
        private TextView tvTagRed1;             //红色的标签1
        private TextView tvPrice;               //价格

        public ViewHolder(View itemView)
        {

            super(itemView);

            Log.i("xiaobaicai","创建元素1");
            //添加鼠标监听
            itemView.setOnClickListener(itemClickListener);

            rlContainer = itemView.findViewById(R.id.product_rl_container);
            imageView = itemView.findViewById(R.id.product_iv);
            tipTag = itemView.findViewById(R.id.product_tv_tag);
            tvTitle = itemView.findViewById(R.id.product_tv_title);
            tvSubTitle = itemView.findViewById(R.id.product_tv_subtitle);
            llTagContainer = itemView.findViewById(R.id.ll_tag_container);
            tvTagBlack1 = itemView.findViewById(R.id.product_tv_tag_out_black_1);
            tvTagBlack2 = itemView.findViewById(R.id.product_tv_tag_out_black_2);
            tvTagRed1 = itemView.findViewById(R.id.product_tv_tag_out_regular_1);
            tvPrice = itemView.findViewById(R.id.product_tv_price);
        }
    }

    //子元素的点击事件
    private View.OnClickListener itemClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){

            Log.i("xiaobaicai","点击了子元素");
        }
    };
}