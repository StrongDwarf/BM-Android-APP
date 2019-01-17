package com.app.bm.bm.common.items;

public class IndexInterestItem {
    private Integer imgData;
    private String text;
    private String id;
    private String url;

    public IndexInterestItem(){}

    public IndexInterestItem(String id, Integer imgData, String text,String url){
        this.id = id;
        this.imgData = imgData;
        this.text = text;
        this.url = url;
    }

    public String getUrl() {
        return url;
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

    public void setUrl(String url) {
        this.url = url;
    }
}