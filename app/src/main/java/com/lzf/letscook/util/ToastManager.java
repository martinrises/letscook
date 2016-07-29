package com.lzf.letscook.util;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

/**
 * Created by liuzhaofeng on 16/7/29.
 */
public class ToastManager {

    private static int mLastResId = Integer.MAX_VALUE;
    private static long mLastShowToastTime = 0;

    public static void makeAndShowText(Context context, @StringRes int resId, @Snackbar.Duration int duration) throws Resources.NotFoundException {

        if(System.currentTimeMillis() - mLastShowToastTime < 1000 && mLastResId == resId){
            return;
        }
        mLastResId = resId;
        mLastShowToastTime = System.currentTimeMillis();

        Toast.makeText(context, resId, duration).show();
    }
}
