package com.lzf.letscook.util;

import android.util.Log;

import com.lzf.letscook.BuildConfig;

/**
 * Created by liuzhaofeng on 16/4/30.
 */
public class Logger {

    private static boolean DEBUG = BuildConfig.DEBUG;

    public static void v(String tag, String msg){
        if(DEBUG){
            Log.v(tag, msg);
        }
    }
}
