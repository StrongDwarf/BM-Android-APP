package com.app.bm.bm.entity;

/**
 * 首页banner item数据
 * create by xiaobaicai on 2018-12-24
 */


public class HomeBannerItem {
    private Integer imgData;    //图片数据地址
    private String title;       //banner标题
    private String intro;       //banner介绍
    private String innerTitle;  //banner内部标题
    private String innerIntro;  //banner内部介绍
    private String id;          //banner ID用于点击后向后台请求数据

    public HomeBannerItem(){}

    public HomeBannerItem(String id, Integer imgData, String title, String intro, String innerTitle, String innerIntro){
        this.imgData = imgData;
        this.title = title;
        this.intro = intro;
        this.innerTitle = innerTitle;
        this.innerIntro = innerIntro;
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImgData(Integer imgData) {
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

    public Integer getImgData() {
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

}