package com.app.bm.bm.common.datas;

import java.util.List;

/**
 * 产品 item
 */
public class ProductItemData{
    private String title;
    private List<ProductListItemData> list;

    public ProductItemData(){}

    public ProductItemData(String title,List<ProductListItemData> list){
        this.title = title;
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public List<ProductListItemData> getList() {
        return list;
    }

    public void setList(List<ProductListItemData> list) {
        this.list = list;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}