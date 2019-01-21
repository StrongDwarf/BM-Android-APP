package com.app.bm.bm.common.datas;

import java.util.List;

/**
 * 首页article_list数据,用于渲染 斑马发现下面的banner
 */
public class HomeArticleListData{
    private String title;
    private List<HomeArticleListItemData> list;

    public String getTitle() {
        return title;
    }

    public List<HomeArticleListItemData> getList() {
        return list;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setList(List<HomeArticleListItemData> list) {
        this.list = list;
    }



}