package com.lzf.letscook.net;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.util.Logger;
import com.lzf.letscook.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by liuzhaofeng on 16/5/3.
 */
public class NetApi {

    public static Observable<List<Recipe>> getRecipesOnline(final String query, final String order, final int start, final int size) {

        final SubscriberHolder holder = new SubscriberHolder();
        Observable<List<Recipe>> ob = Observable.create(new Observable.OnSubscribe<List<Recipe>>() {
            @Override
            public void call(Subscriber<? super List<Recipe>> subscriber) {

                holder.setSubscriber(subscriber);
            }
        });

        String url = UrlContainer.getSearchRecipeUrl() + start + "/" + size;
        Map<String, String> params = new HashMap<>();
        params.put("order", order);
        params.put("tag", query);
        params.put("client", "7");
        Utils.signParam(url, params);

        RecipeRequest req = new RecipeRequest(Request.Method.POST, url, UrlContainer.getHeaders(), params, new Response.Listener<List<Recipe>>() {
            @Override
            public void onResponse(List<Recipe> response) {
                notifySubcriber(response, holder, start);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                notifySubcriber(null, holder, start);
            }
        });
        ReqQueue.getInstance().add(req);

        return ob;
    }

    private static void notifySubcriber(List<Recipe> response, SubscriberHolder holder, int start) {
        Logger.v("test", "NetApi >>>>>> " + start+ "   "+ (Utils.isCollectionEmpty(response) ? "" : response.get(0).getCook_id()));
        Subscriber sub = holder.getSubscriber();
        if (sub != null) {
            sub.onNext(response);
        }
    }
}
