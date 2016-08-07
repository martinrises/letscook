package com.lzf.letscook;

import android.app.Application;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;

import io.fabric.sdk.android.Fabric;

/**
 * Created by liuzhaofeng on 16/4/30.
 */
public class LetsCook extends Application {

    public static final int CLIENT_ID = 7; // AndroidManifest中配置的CLIENT_ID

    private static LetsCook sApp;

    public static int heightPixels;
    public static int widthPixels;

    @Override
    public void onCreate() {
        super.onCreate();

        sApp = this;
        Fresco.initialize(this);

        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }

        initDisplay();

    }

    private void initDisplay() {
        Resources res = getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();

        heightPixels = displayMetrics.heightPixels;
        widthPixels = displayMetrics.widthPixels;
    }

    public static LetsCook getApp() {
        return sApp;
    }


}
