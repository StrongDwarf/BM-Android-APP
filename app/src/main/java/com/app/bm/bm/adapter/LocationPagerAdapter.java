package com.app.bm.bm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager适配器
 * @author xiaobaicai
 * @create time 2018-12-11
 */
public class LocationPagerAdapter extends FragmentStatePagerAdapter{
    private final List<Fragment> fragmentList = new ArrayList<Fragment>();
    private final List<String> fragmentTitleList = new ArrayList<String>();

    public LocationPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragmentList.get(arg0);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }
}