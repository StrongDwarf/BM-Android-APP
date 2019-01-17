package com.app.bm.bm.common.datas;

public class AjaxReceiveData{
    private int code;
    private String message;
    private Object data;

    public AjaxReceiveData(){};
    public AjaxReceiveData(int code,String message,Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}