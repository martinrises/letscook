package com.lzf.letscook.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzf.letscook.R;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.ui.view.ShopRecipeView;
import com.lzf.letscook.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhaofeng on 16/7/14.
 */
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopHolder> {

    private static final String TAG = ShopAdapter.class.getSimpleName();

    private LayoutInflater mInflator;
    private final List<Recipe> mRecipes = new ArrayList<>();

    @Override
    public ShopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mInflator == null){
            mInflator = LayoutInflater.from(parent.getContext());
        }
        View view = mInflator.inflate(R.layout.item_shop, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ShopHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopHolder holder, int position) {
        holder.mShopRecipeView.setRecipe(mRecipes.get(position));
    }

    @Override
    public int getItemCount() {
        int cnt = 0;
        if(mRecipes == null){
//            return 0;
            cnt = 0;
        }
//        return mRecipes.size();
        cnt = mRecipes.size();
        Logger.v(TAG, "ShopAdapter.getItemCount() >>> " + cnt);
        return cnt;
    }

    public void setData(List<Recipe> recipes){
        mRecipes.clear();
        if(recipes != null) {
            mRecipes.addAll(recipes);
        }
    }

    static final class ShopHolder extends RecyclerView.ViewHolder{

        ShopRecipeView mShopRecipeView;

        public ShopHolder(View itemView) {
            super(itemView);
            mShopRecipeView = (ShopRecipeView) itemView;
        }
    }
}
