package com.app.bm.bm.common.datas;

public class HomeBannerItemData{
    private String img;     //img地址
    private String url;     //url地址
    private String title;   //title eg:"为新年加油 | 全线优惠中"
    private String subtitle;    //subtitle eg:"辛苦一年是时候犒劳自己和家人啦，出游早计划，行前不心慌，还有限时优惠"

    public HomeBannerItemData(){};

    public HomeBannerItemData(String img,String url,String title,String subtitle){
        this.img = img;
        this.url = url;
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public String getImg() {
        return img;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getUrl() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}