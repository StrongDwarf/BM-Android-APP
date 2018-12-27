package com.app.bm.bm.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.bm.bm.R;
/*
import com.app.bm.bm.adapter.FragmentAdapter;
import com.app.bm.bm.adapter.LocationPagerAdapter;
import com.app.bm.bm.fragment.location.PageLocation;
import com.app.bm.bm.fragment.location.PageTheme;
*/
public class MainDestinationFragment extends Fragment {
    /*
    private ViewPager viewPager;
    private PageLocation pageLocation;
    private PageTheme pageTheme;
    private LocationPagerAdapter locationPagerAdapter;
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate){
        View rootView = inflater.inflate(R.layout.main_destination_fragment,null);

        /*
        viewPager = (ViewPager) rootView.findViewById(R.id.location_viewPager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);

        locationPagerAdapter = new LocationPagerAdapter(getActivity().getSupportFragmentManager());

        pageLocation  = new PageLocation();
        pageTheme = new PageTheme();
        locationPagerAdapter.addFragment(pageLocation,"目的地");
        locationPagerAdapter.addFragment(pageTheme,"兴趣主题");

        viewPager.setAdapter(locationPagerAdapter);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.location_tabLayout);
        tabLayout.setupWithViewPager(viewPager);
*/
        return rootView;
    }
}