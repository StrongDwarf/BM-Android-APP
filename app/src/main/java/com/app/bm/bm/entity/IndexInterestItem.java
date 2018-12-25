package com.app.bm.bm.entity;

public class IndexInterestItem{
    private Integer imgData;
    private String text;
    private String id;

    public IndexInterestItem(){}

    public IndexInterestItem(String id,Integer imgData,String text){
        this.id = id;
        this.imgData = imgData;
        this.text = text;
    }

    public Integer getImgData() {
        return imgData;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setImgData(Integer imgData) {
        this.imgData = imgData;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }
}