package com.app.bm.bm.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import com.app.bm.bm.R;
import com.app.bm.bm.adapter.FragmentAdapter;
import com.app.bm.bm.fragment.find.FindAllFragment;
import com.app.bm.bm.fragment.find.FindLookworldFragment;
import com.app.bm.bm.fragment.find.FindRiseknowFragment;
import com.app.bm.bm.fragment.find.FindTalentsayFragment;

public class DiscoverFragment extends Fragment {

    private ViewPager vPager;
    private FindAllFragment findAllFragment;
    private FindLookworldFragment findLookworldFragment;
    private FindRiseknowFragment findRiseknowFragment;
    private FindTalentsayFragment findTalentsayFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discover, null);

        vPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        vPager.setOffscreenPageLimit(3);
        vPager.setCurrentItem(0);

        FragmentAdapter pagerAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager());

        findAllFragment = new FindAllFragment();
        findLookworldFragment = new FindLookworldFragment();
        findRiseknowFragment = new FindRiseknowFragment();
        findTalentsayFragment = new FindTalentsayFragment();
        pagerAdapter.addFragment(findAllFragment,"全部");
        pagerAdapter.addFragment(findRiseknowFragment,"涨知识");
        pagerAdapter.addFragment(findLookworldFragment,"看世界");
        pagerAdapter.addFragment(findTalentsayFragment,"达人说");

        vPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(vPager);



        return rootView;
    }

}