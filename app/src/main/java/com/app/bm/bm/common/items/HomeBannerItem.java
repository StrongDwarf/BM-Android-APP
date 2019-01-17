package com.app.bm.bm.common.items;

import android.graphics.Bitmap;

/**
 * 首页banner item数据
 * create by xiaobaicai on 2018-12-24
 */


public class HomeBannerItem {
    private Bitmap imgData;    //图片数据
    private String title;       //banner标题
    private String intro;       //banner介绍
    private String innerTitle;  //banner内部标题
    private String innerIntro;  //banner内部介绍
    private String id;          //banner ID用于点击后向后台请求数据
    private String url;         //banner 点击后跳转的url

    public HomeBannerItem(){}

    public HomeBannerItem(String id,String title,String intro,String url){
        this.id = id;
        this.title = title;
        this.intro = intro;
        this.url = url;
    }

    public HomeBannerItem(String id,Bitmap imgData,String title,String intro,String url){
        this.id = id;
        this.imgData = imgData;
        this.title = title;
        this.intro = intro;
        this.url = url;
    }

    public HomeBannerItem(String id, Bitmap imgData, String title, String intro, String innerTitle, String innerIntro){
        this.imgData = imgData;
        this.title = title;
        this.intro = intro;
        this.innerTitle = innerTitle;
        this.innerIntro = innerIntro;
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImgData(Bitmap imgData) {
        this.imgData = imgData;
    }

    public void setInnerIntro(String innerIntro) {
        this.innerIntro = innerIntro;
    }

    public void setInnerTitle(String innerTitle) {
        this.innerTitle = innerTitle;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public Bitmap getImgData() {
        return imgData;
    }

    public String getInnerIntro() {
        return innerIntro;
    }

    public String getInnerTitle() {
        return innerTitle;
    }

    public String getIntro() {
        return intro;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}