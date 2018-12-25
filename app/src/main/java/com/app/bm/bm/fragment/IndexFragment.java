package com.app.bm.bm.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.app.bm.bm.R;
import com.app.bm.bm.adapter.IndexGridAdapter;
import com.app.bm.bm.entity.IndexInterestItem;
import com.app.bm.bm.fragment.index.IndexBannerFragment;

import java.util.List;

public class IndexFragment extends Fragment {
    private GridView indexGvIntexest;               //兴趣主题中的GridView页面
    private IndexGridAdapter indexGridAdapter;
    private List<IndexInterestItem> indexInterestItems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate){
        View rootView = inflater.inflate(R.layout.fragment_index,null);

        //设置Banner页面
        Fragment fragment =new IndexBannerFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fra_index_banner,fragment).commit();

        //获取元素
        indexGvIntexest = rootView.findViewById(R.id.index_gv_interst_theme);

        /**
         * 设置interest_view
         */
        //设置图片地址
        int []BANNER = new int[]{R.drawable.ic_interest1,R.drawable.ic_interest2,R.drawable.ic_interest3,R.drawable.ic_interest4,
                R.drawable.ic_interest5,R.drawable.ic_interest6,R.drawable.ic_interest7,R.drawable.ic_interest8};



        return rootView;
    }


}