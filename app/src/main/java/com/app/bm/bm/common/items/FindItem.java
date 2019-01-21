package com.app.bm.bm.common.items;

/**
 * 发现页面的子元素
 * @author xiaobaicai
 * @create time 2018-12-11
 */

public class FindItem {
    private int id; //唯一id
    private String text1;   //find_item中的第一句话
    private String text2;   //find_item中的第二句话
    private String tag;     //find_item的标签:  比如达人说,看世界
    private String imgUrl;  //find_item的图片地址

    public FindItem(){}

    public FindItem(int id, String text1, String text2, String tag, String imgUrl){
        this.id=id;
        this.text1 = text1;
        this.text2 = text2;
        this.tag = tag;
        this.imgUrl = imgUrl;
    }

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public String getText1(){return text1;}

    public void setText1(String text1){this.text1 = text1;}

    public String getText2(){return text2;}

    public void setText2(String text2){this.text2 = text2;}

    public String getTag(){return tag;}

    public void setTag(String tag){this.tag = tag;}

    public String getImgUrl(){return imgUrl;}

    public void setImgUrl(String imgUrl){this.imgUrl = imgUrl;}
}