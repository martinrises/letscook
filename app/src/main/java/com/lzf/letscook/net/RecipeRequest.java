package com.lzf.letscook.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.util.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzhaofeng on 16/4/30.
 */
public class RecipeRequest extends Request<List<Recipe>> {

    private Response.Listener<List<Recipe>> mListener;
    private Map<String, String> mHeaders;
    private Map<String, String> mParams;

    public RecipeRequest(int method, String url, Map<String, String> headers, Map<String, String> params, Response.Listener<List<Recipe>> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
        this.mHeaders = headers;
        this.mParams = params;
    }

    @Override
    protected Response<List<Recipe>> parseNetworkResponse(NetworkResponse response) {

        String json = new String(response.data);
        Logger.v("responseTag", json);
        // 序列化List<Recipe>
        List<Recipe> recipes = ParseUtils.parseRecipes(json);
        return Response.success(recipes, null);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if(mHeaders != null){
            return mHeaders;
        }
        return super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if(mParams != null){
            return mParams;
        }
        return super.getParams();
    }

    @Override
    protected void deliverResponse(List<Recipe> response) {

        if(mListener != null){
            mListener.onResponse(response);
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
    }
}
