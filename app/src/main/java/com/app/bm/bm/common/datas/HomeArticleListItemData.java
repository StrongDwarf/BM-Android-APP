package com.app.bm.bm.common.datas;

public class HomeArticleListItemData{
    private int id;
    private String url;
    private String title;
    private String img;
    private int type;
    private String h5_link;

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getImg() {
        return img;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getH5_link() {
        return h5_link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setH5_link(String h5_link) {
        this.h5_link = h5_link;
    }

    public void setType(int type) {
        this.type = type;
    }
}