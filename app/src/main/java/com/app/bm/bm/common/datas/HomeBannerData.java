package com.app.bm.bm.common.datas;

import java.util.List;

/**
 * 首页Banner数据
 * create by xiaobaicai on 2019-1-15
 */
public class HomeBannerData{
    private int isMain;     //指示默认展示那张图片
    private List<HomeBannerItemData> list;

    public HomeBannerData(){}

    public HomeBannerData(int isMain,List<HomeBannerItemData> list){
        this.isMain = isMain;
        this.list = list;
    }

    public int getIsMain() {
        return isMain;
    }

    public List<HomeBannerItemData> getList() {
        return list;
    }

    public void setIsMain(int isMain) {
        this.isMain = isMain;
    }

    public void setList(List<HomeBannerItemData> list) {
        this.list = list;
    }

    public HomeBannerItemData getItem(int i){
        return list.get(i);
    }

}