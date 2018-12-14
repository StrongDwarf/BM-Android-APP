package com.app.bm.bm.fragment.location;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.bm.bm.MainActivity;
import com.app.bm.bm.R;
import com.app.bm.bm.adapter.LocationListviewAdapter;
import com.app.bm.bm.adapter.LocationThemeListviewAdapter;
import com.app.bm.bm.entity.ButtonItem;
import com.app.bm.bm.entity.LocationItem;

import java.util.ArrayList;
import java.util.List;

public class PageTheme extends Fragment {

    private ListView listView;
    private LocationThemeListviewAdapter locationThemeListviewAdapter;
    private List<LocationItem> locationItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate){
        View rootView = inflater.inflate(R.layout.page_location_theme,null);

        initData();

        listView = (ListView)rootView.findViewById(R.id.location_listview);
        locationThemeListviewAdapter = new LocationThemeListviewAdapter(this.getContext(),locationItems);
        listView.setAdapter(locationThemeListviewAdapter);

        //listView.setOnItemClickListener(onItemClickListener);
        return rootView;
    }

    public AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i("xiaobaicai","点击了"+position);
        }
    };

    //初始化数据
    private void initData(){
        locationItems=new ArrayList<>();
        for(int i=0;i<5;i++){
            List<ButtonItem> buttonItems = new ArrayList<>();
            for(int j=0;j<10;j++){
                ButtonItem buttomItem = new ButtonItem(i*j,"卡莎布兰卡"+j);
                buttonItems.add(buttomItem);
            }
            LocationItem locationItem = new LocationItem(i,"香港",buttonItems);
            locationItems.add(locationItem);
        }
    }
}