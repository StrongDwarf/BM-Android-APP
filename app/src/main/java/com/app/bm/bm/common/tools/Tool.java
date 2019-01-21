package com.app.bm.bm.common.tools;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class Tool{
    public static int getPixelsFromDp(Activity activity, int size){

        DisplayMetrics metrics =new DisplayMetrics();

        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return(size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;

    }
}