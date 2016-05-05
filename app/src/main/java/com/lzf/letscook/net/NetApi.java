package com.lzf.letscook.net;

import com.android.volley.Request;
import com.android.volley.toolbox.RequestFuture;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by liuzhaofeng on 16/5/3.
 */
public class NetApi {

    public static List<Recipe> getRecipesOnline(String query, String order, int start, int size) throws ExecutionException, InterruptedException {

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
}
