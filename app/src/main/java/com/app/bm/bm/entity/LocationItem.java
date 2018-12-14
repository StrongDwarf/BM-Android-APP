package com.app.bm.bm.entity;

import java.util.List;

/**
 * 发现页面的子元素
 * @author xiaobaicai
 * @create time 2018-12-11
 */

public class LocationItem {
    private int id; //唯一id
    private String title;  //文字
    private List<ButtonItem> buttonItemList;

    public LocationItem(){}

    public LocationItem(int id, String title, List buttonItemList){
        this.id=id;
        this.title = title;
        this.buttonItemList = buttonItemList;
    }

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public String getTitle(){return title;}

    public void setTitle(String title){this.title = title;}

    public List getButtonItemList(){
        return buttonItemList;
    }

    public void setButtonItemList(List buttonItemList){
        this.buttonItemList = buttonItemList;
    }
}