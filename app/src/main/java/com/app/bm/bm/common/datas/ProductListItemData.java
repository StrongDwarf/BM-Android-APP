package com.app.bm.bm.common.datas;

import java.util.List;

public class ProductListItemData{
    private int id;
    private String img;
    private String title;
    private String subtitle;
    private String price;
    private String url;
    private String tip_mark;
    private List<ProductMarkItem> mark;

    public ProductListItemData(){}

    public ProductListItemData(int id,String img,String title,String subtitle,String price,String url,String tip_mark,List<ProductMarkItem> mark){
        this.id = id;
        this.img = img;
        this.title = title;
        this.subtitle = subtitle;
        this.price = price;
        this.url = url;
        this.tip_mark = tip_mark;
        this.mark = mark;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getImg() {
        return img;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public List<ProductMarkItem> getMark() {
        return mark;
    }

    public String getPrice() {
        return price;
    }

    public String getTip_mark() {
        return tip_mark;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setMark(List<ProductMarkItem> mark) {
        this.mark = mark;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTip_mark(String tip_mark) {
        this.tip_mark = tip_mark;
    }
}