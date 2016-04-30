package com.lzf.letscook.system;

import android.os.Handler;
import android.os.Message;

import com.android.volley.Request;
import com.android.volley.toolbox.RequestFuture;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.net.RecipeRequest;
import com.lzf.letscook.net.ReqQueue;
import com.lzf.letscook.net.UrlContainer;
import com.lzf.letscook.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by liuzhaofeng on 16/4/30.
 */
public class CookSystem {

    private static CookSystem sInstance;
    private Handler mHandler = new CookHandler();

    public static CookSystem getInstance() {
        if (sInstance == null) {
            sInstance = new CookSystem();
        }
        return sInstance;
    }

    private CookSystem() {
    }

    public List<Recipe> getRecipes(String query, String order, int start, int size) throws ExecutionException, InterruptedException {

        RequestFuture<List<Recipe>> future = RequestFuture.newFuture();

        String url = UrlContainer.getSearchRecipeUrl() + start + "/" + size;

        Map<String, String> params = new HashMap<>();
        params.put("order", order);
        params.put("tag", query);
        params.put("client", "7");
        Utils.signParam(url, params);
        RecipeRequest req = new RecipeRequest(Request.Method.POST, url, UrlContainer.getHeaders(), params, future, future);
        ReqQueue.getInstance().add(req);

        return future.get();
    }

    private static final class CookHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

        }
    }
}
