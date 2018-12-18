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
import com.app.bm.bm.entity.ButtonItem;
import com.app.bm.bm.entity.ElemListView;
import com.app.bm.bm.entity.LocationItem;

import java.util.ArrayList;
import java.util.List;

public class PageLocation extends Fragment {

    private ElemListView listView;
    private LocationListviewAdapter locationListviewAdapter;
    private List<LocationItem> locationItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate){
        View rootView = inflater.inflate(R.layout.page_location_location,null);

        initData();

        listView = (ElemListView)rootView.findViewById(R.id.location_listview);
        locationListviewAdapter = new LocationListviewAdapter(this.getContext(),locationItems);
        listView.setAdapter(locationListviewAdapter);

        return rootView;
    }

    //初始化数据
    private void initData(){
        String[][] locationDatalists = new String[][]{
                {"亚洲","欧美","海岛"},
                {"日本","巴厘岛","新加坡","越南","马来西亚","泰国","斯里兰卡","柬埔寨","坦桑尼亚","北极"},
                {"欧洲","美国","加拿大","南美","土耳其"},
                {"毛里求斯","马尔代夫","斐济","塞舌尔共和国","塞班岛"}};

        locationItems=new ArrayList<>();
        int length = locationDatalists[0].length+1;
        for(int i=1;i<length;i++){
            List<ButtonItem> buttonItems = new ArrayList<>();
            int temp = locationDatalists[i].length - 1;
            for(int j=0;j<temp;j++){
                ButtonItem buttomItem = new ButtonItem(j,locationDatalists[i][j]);
                buttonItems.add(buttomItem);
            }
            LocationItem locationItem = new LocationItem(i,locationDatalists[0][i-1],buttonItems);
            locationItems.add(locationItem);
        }

    }
}