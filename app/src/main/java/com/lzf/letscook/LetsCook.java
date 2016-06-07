package com.lzf.letscook;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.lzf.letscook.system.FavSystem;

import io.fabric.sdk.android.Fabric;

/**
 * Created by liuzhaofeng on 16/4/30.
 */
public class LetsCook extends Application {

    public static final int CLIENT_ID = 7; // AndroidManifest中配置的CLIENT_ID

    private static LetsCook sApp;

    @Override
    public void onCreate() {
        super.onCreate();

        sApp = this;
        Fresco.initialize(this);

        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }

        FavSystem.getInstance().init();
    }

    public static LetsCook getApp() {
        return sApp;
    }
}
