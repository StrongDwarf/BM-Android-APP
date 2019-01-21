package com.app.bm.bm.common.datas;

import java.util.List;

public class HomePageData{
    private HomeBannerData banner;
    private List<HomeThemeListItemData> theme_list;
    private HomeActivityData activity;
    private List<ProductItemData> product;
    private HomeArticleListData article_list;

    public HomeActivityData getActivity() {
        return activity;
    }

    public HomeArticleListData getArticle_list() {
        return article_list;
    }

    public HomeBannerData getBanner() {
        return banner;
    }

    public List<ProductItemData> getProduct() {
        return product;
    }

    public List<HomeThemeListItemData> getTheme_list() {
        return theme_list;
    }

    public void setActivity(HomeActivityData activity) {
        this.activity = activity;
    }

    public void setArticle_list(HomeArticleListData article_list) {
        this.article_list = article_list;
    }

    public void setBanner(HomeBannerData banner) {
        this.banner = banner;
    }

    public void setProduct(List<ProductItemData> product) {
        this.product = product;
    }

    public void setTheme_list(List<HomeThemeListItemData> theme_list) {
        this.theme_list = theme_list;
    }

    private class HomeThemeListItemData{
        private String img;     //图片地址
        private String title;   //名称 eg:"家庭亲子"
        private int id;
        private String url;

        public HomeThemeListItemData(){};

        public HomeThemeListItemData(String img,String title,int id,String url){
            this.img = img;
            this.title = title;
            this.id = id;
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public String getImg() {
            return img;
        }

        public String getTitle() {
            return title;
        }

        public int getId() {
            return id;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}