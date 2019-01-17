package com.app.bm.bm.common.extend.elem;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * GridView扩展,
 * 修改原GridView onMeasure() 测量错误 BUG
 * create by xiaobaicai on 2019-1-17
 */
public class ElemGridView extends GridView{
    public ElemGridView(Context context){
        super(context);
    }

    public ElemGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public ElemGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}