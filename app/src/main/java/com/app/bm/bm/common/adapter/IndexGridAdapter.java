package com.app.bm.bm.common.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.bm.bm.R;
import com.app.bm.bm.common.items.IndexInterestItem;

import java.util.List;

public class IndexGridAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private List<IndexInterestItem> indexInterestItems;

    public IndexGridAdapter(Context context,List<IndexInterestItem> indexInterestItems){
        inflater = LayoutInflater.from(context);
        this.indexInterestItems = indexInterestItems;
    }

    @Override
    public int getCount(){
        return indexInterestItems.size();
    }

    @Override
    public Object getItem(int position){
        return indexInterestItems.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_index_interest,parent,false);
            viewHolder.linearLayout = (LinearLayout) convertView.findViewById(R.id.ll_interest_container);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_interest_item);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_interest_item);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /*
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.setSelected(true);
                Log.i("xiaobaicai","点击了"+position+"个");
            }
        });
*/
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("xiaobaicai","点击了首页的兴趣子元素"+position+":该元素的url是"+indexInterestItems.get(position).getUrl());
            }
        });

        viewHolder.textView.setText(indexInterestItems.get(position).getText());
        viewHolder.imageView.setImageResource(indexInterestItems.get(position).getImgData());
        return convertView;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private class ViewHolder{
        private LinearLayout linearLayout;      //父容器.
        private ImageView imageView;        //图片
        private TextView textView;          //文字
    }
}