package com.app.bm.bm.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.bm.bm.R;
import com.app.bm.bm.entity.ButtonItem;
import com.app.bm.bm.entity.ElemLocationButton;
import com.app.bm.bm.entity.LocationItem;

import java.util.List;

public class LocationListviewAdapter extends BaseAdapter{
    private List<LocationItem> locationItems;
    private LayoutInflater inflater;

    public LocationListviewAdapter(Context context, List<LocationItem> locationItems){
        inflater = LayoutInflater.from(context);
        this.locationItems = locationItems;
    }

    //数据源长度
    @Override
    public int getCount(){
        return locationItems.size();
    }

    //每一行的绑定数据源
    @Override
    public Object getItem(int position){
        return locationItems.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    //获取每一行的View
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView ==null){
            viewHolder = new ViewHolder();
            //xml文件加载成View
            convertView = inflater.inflate(R.layout.item_location,parent,false);

            viewHolder.linearLayout = (LinearLayout) convertView.findViewById(R.id.location_linearlayout);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.location_title);
            viewHolder.gridView = (GridView) convertView.findViewById(R.id.location_gridview);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.textView.setText(locationItems.get(position).getTitle());
        //设置gridView的适配器

        LocationGridButtonAdapter locationGridButtonAdapter = new LocationGridButtonAdapter(parent.getContext(),locationItems.get(position).getButtonItemList());
        //设置gridView的高
        viewHolder.gridView.setAdapter(locationGridButtonAdapter);

        return convertView;
    }

    private class ViewHolder{
        private LinearLayout linearLayout;
        private TextView textView;
        private GridView gridView;
    }

    public int getHeight(List<ButtonItem> buttonItemList){
        int num1 = buttonItemList.size()/3;
        if(buttonItemList.size()%3 == 0){
            return 50*num1;
        }else{
            return 50*(num1+1);
        }
    }
}