package com.app.bm.bm.entity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.app.bm.bm.R;

public class ElemLocationButton extends AppCompatButton {
    private String myid;

    public ElemLocationButton(Context context){
        super(context);
    }

    @SuppressLint("Recycle")
    public ElemLocationButton(Context context, AttributeSet attrs) {
        super(context, attrs);


        TypedArray t = getContext().obtainStyledAttributes(attrs,R.styleable.ElemLocationButton);
        myid = t.getString(R.styleable.ElemLocationButton_myid);

    }

    public ElemLocationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getMyid(){
        return myid;
    }

    public void setMyid(String myid){
        this.myid = myid;
    }


}