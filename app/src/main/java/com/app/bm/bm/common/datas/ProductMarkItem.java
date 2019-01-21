package com.app.bm.bm.common.datas;

public class ProductMarkItem{
    private String name;    //eg:私家游
    private int flag;       //flag:0，正常标题,  flag:1,标红

    public ProductMarkItem(){}
    public ProductMarkItem(String name,int flag){
        this.name = name;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public int getFlag() {
        return flag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}