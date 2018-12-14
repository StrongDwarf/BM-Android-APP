package com.app.bm.bm.entity;

/**
 * 一个按钮元素的数据
 * @author xiaobaicai
 * @create time 2018-12-14
 */

public class ButtonItem {
    private int id;         //按钮唯一id
    private String text;       //按钮上的文字

    public ButtonItem(){}

    public ButtonItem(int id, String text){
        this.id=id;
        this.text = text;
    }

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public String getText(){return text;}

    public void setText(String text){this.text = text;}
}