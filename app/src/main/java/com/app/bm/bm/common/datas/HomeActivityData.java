package com.app.bm.bm.common.datas;

import java.util.List;

/**
 * 近期活动数据
 * create by xiaobaicai on 2019-1-15
 */
public class HomeActivityData{
    private String title;
    private List<HomeActivityItemData> list;

    public HomeActivityData(){};

    public HomeActivityData(String title,List<HomeActivityItemData> list){
        this.title = title;
        this.list = list;
    };

    public String getTitle() {
        return title;
    }

    public List<HomeActivityItemData> getList() {
        return list;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setList(List<HomeActivityItemData> list) {
        this.list = list;
    }
}