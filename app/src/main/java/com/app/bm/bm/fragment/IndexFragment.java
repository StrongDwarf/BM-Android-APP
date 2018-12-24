package com.app.bm.bm.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.bm.bm.R;
import com.app.bm.bm.fragment.index.IndexBannerFragment;

public class IndexFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate){
        View rootView = inflater.inflate(R.layout.fragment_index,null);

        Fragment fragment =new IndexBannerFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fra_index_banner,fragment).commit();


        return rootView;
    }
}