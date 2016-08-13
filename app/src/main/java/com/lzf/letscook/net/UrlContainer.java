package com.lzf.letscook.net;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

import com.lzf.letscook.LetsCook;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by liuzhaofeng on 16/4/30.
 */
public class UrlContainer {

    public static final String HOST = "http://api.douguo.net";

    public static final String TAG_SEARCH = "/recipe/tagsearch/";

    public static final String SEARCH = "/recipe/search/";

    public static Random mRandom = new Random();

    private static Map<String, String > PARAMS = null;

    public static Map<String, String > getHeaders(){

        if(PARAMS == null){
            PARAMS = initHeaders();
        }

        return PARAMS;
    }

    private static Map<String, String> initHeaders() {

        Map<String, String> params = new HashMap<>();
        params.put("device", Build.MODEL);
        params.put("sdk", Build.VERSION.SDK_INT + "," + Build.VERSION.RELEASE);

        LetsCook app = LetsCook.getApp();
        params.put("dpi", LetsCook.density + "");

        if(ContextCompat.checkSelfPermission(LetsCook.getApp(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){
            TelephonyManager tm = (TelephonyManager) app.getSystemService(Context.TELEPHONY_SERVICE);
            params.put("imei", tm.getDeviceId());
        }

        try {
            WifiManager wm = (WifiManager) app.getSystemService(Context.WIFI_SERVICE);
            WifiInfo connectionInfo = wm.getConnectionInfo();
            params.put("mac", connectionInfo.getMacAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }

        params.put("resolution", LetsCook.heightPixels + "*" + LetsCook.widthPixels);
        params.put("client", LetsCook.CLIENT_ID + "");
        params.put("version", "121");
        params.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        params.put("Accept-Encoding", "gzip, deflate");

        return params;
    }

    public static String getTagSearchRecipeUrl(){
        return HOST + TAG_SEARCH;
    }

    public static String getSearchRecipeUrl(){
        return HOST + SEARCH;
    }

}
