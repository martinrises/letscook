package com.lzf.letscook.net;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.lzf.letscook.LetsCook;

/**
 * Created by liuzhaofeng on 16/4/30.
 */
public class ReqQueue {

    private static RequestQueue requestQueue = null;

    public static RequestQueue getInstance(){

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(LetsCook.getApp());
        }

        return requestQueue;
    }
}
