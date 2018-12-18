package com.app.bm.bm.dataitem;

public class CustomizeOrderData{
    private String id;
    private String endCity;
    private String startCity;
    private String startTime;
    private String endTime;
    private int numAdult;
    private int numChild;
    private String name;
    private int phone;
    private String email;
    private String wechatNumber;
    private String startFitAnswer;
    private String endFitAnswer;
    private String otherNeed;

    public CustomizeOrderData(){}

    public CustomizeOrderData(String id,String endCity,String startCity,String startTime,String endTime,
                              int numAdult,int numChild,String name,int phone,String email,String wechatNumber,
                              String startFitAnswer, String endFitAnswer,String otherNeed){
        this.id = id;
        this.endCity = endCity;
        this.startCity = startCity;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numAdult = numAdult;
        this.numChild = numChild;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.wechatNumber = wechatNumber;
        this.startFitAnswer = startFitAnswer;
        this.endFitAnswer = endFitAnswer;
        this.otherNeed = otherNeed;
    }

    public CustomizeOrderData(String endCity,String startCity,String startTime,String endTime,
                              int numAdult,int numChild,String name,int phone,String email,String wechatNumber,
                              String startFitAnswer, String endFitAnswer,String otherNeed){
        this.endCity = endCity;
        this.startCity = startCity;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numAdult = numAdult;
        this.numChild = numChild;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.wechatNumber = wechatNumber;
        this.startFitAnswer = startFitAnswer;
        this.endFitAnswer = endFitAnswer;
        this.otherNeed = otherNeed;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    public void setEndFitAnswer(String endFitAnswer) {
        this.endFitAnswer = endFitAnswer;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumAdult(int numAdult) {
        this.numAdult = numAdult;
    }

    public void setNumChild(int numChild) {
        this.numChild = numChild;
    }

    public void setOtherNeed(String otherNeed) {
        this.otherNeed = otherNeed;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public void setStartFitAnswer(String startFitAnswer) {
        this.startFitAnswer = startFitAnswer;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setWechatNumber(String wechatNumber) {
        this.wechatNumber = wechatNumber;
    }

    public int getNumAdult() {
        return numAdult;
    }

    public int getNumChild() {
        return numChild;
    }

    public int getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getEndCity() {
        return endCity;
    }

    public String getEndFitAnswer() {
        return endFitAnswer;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOtherNeed() {
        return otherNeed;
    }

    public String getStartCity() {
        return startCity;
    }

    public String getStartFitAnswer() {
        return startFitAnswer;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getWechatNumber() {
        return wechatNumber;
    }

}
