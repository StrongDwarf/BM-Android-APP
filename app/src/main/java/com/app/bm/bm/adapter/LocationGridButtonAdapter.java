package com.app.bm.bm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.app.bm.bm.R;
import com.app.bm.bm.entity.ButtonItem;

import java.util.List;

public class LocationGridButtonAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private List<ButtonItem> buttonItemList;

    public LocationGridButtonAdapter(Context context, List<ButtonItem> buttonItemList){
        inflater = LayoutInflater.from(context);
        this.buttonItemList = buttonItemList;
    }

    @Override
    public int getCount(){
        return buttonItemList.size();
    }

    @Override
    public Object getItem(int position){
        return buttonItemList.get(position);
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

            convertView = inflater.inflate(R.layout.item_location_button,parent,false);
            viewHolder.button = (Button) convertView.findViewById(R.id.btn);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * 处理点击逻辑
                 */
                v.setSelected(true);


                Log.i("xiaobaicai","点击了"+position+"个");
            }
        });
        viewHolder.button.setText(buttonItemList.get(position).getText());
        return convertView;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private class ViewHolder{
        private Button button;
    }
}