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
    private List<MarkItem> mark;

    private class MarkItem{
        private String name;    //eg:私家游
        private int flag;       //flag:0，正常标题,  flag:1,标红

        public MarkItem(){}
        public MarkItem(String name,int flag){
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
}