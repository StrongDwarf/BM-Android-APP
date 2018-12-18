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
import com.app.bm.bm.entity.ElemListView;
import com.app.bm.bm.entity.LocationItem;

import java.util.ArrayList;
import java.util.List;

public class PageTheme extends Fragment {

    private ElemListView listView;
    private LocationThemeListviewAdapter locationThemeListviewAdapter;
    private List<LocationItem> locationItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate){
        View rootView = inflater.inflate(R.layout.page_location_theme,null);

        initData();

        listView = (ElemListView)rootView.findViewById(R.id.location_listview);
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
        String[][] locationDatalists = new String[][]{
                {"跟着电影去旅行","邮轮航线","家庭亲子","动物野趣","浪漫蜜月","海岛风情","特色美食","修身养性","自然奇观","异域风情","奇趣探险"},
                {"卡萨布兰卡","走出非洲","罗马假日","指环王系列","权力的游戏","上帝也疯狂","动漫系列","马达加斯加"},
                {"南北极邮轮","河轮"},
                {"幼儿(3-6)","儿童(7-12)","游学","父母"},
                {"大象","猎豹","大猩猩","企鹅","狐猴","海豚","鲸鱼"},
                {"婚礼婚拍","蜜月度假"},
                {"潜水潜泳","水上活动"},
                {"烤肉牛排","日式料理","米其林"},
                {"Spa","排毒","温泉","修禅"},
                {"极光","雪山峡湾","草原风光","动物大迁徙","国家公园"},
                {"古埃及文明","伊斯兰文明","文艺复兴","玛雅文明"},
                {"冲沙","高空体验","滑雪","徒步登山","国家公园"}};

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