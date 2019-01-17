package com.app.bm.bm.common.datas;


public class HomeActivityItemData{
    private String img;
    private String title;
    private String url;

    public HomeActivityItemData(){}

    public HomeActivityItemData(String img,String title,String url){
        this.img = img;
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getImg() {
        return img;
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

    public void setUrl(String url) {
        this.url = url;
    }
}