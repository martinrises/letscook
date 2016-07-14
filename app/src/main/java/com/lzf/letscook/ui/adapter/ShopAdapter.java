package com.lzf.letscook.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzf.letscook.R;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.ui.view.ShopRecipeView;
import com.lzf.letscook.util.Logger;

import java.util.List;

/**
 * Created by liuzhaofeng on 16/7/14.
 */
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopHolder> {

    private static final String TAG = ShopAdapter.class.getSimpleName();

    private LayoutInflater mInflator;
    private List<Recipe> mRecipes;

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
        Logger.v(TAG, "getItemCount() invoked");
        if(mRecipes == null){
            return 0;
        }
        return mRecipes.size();
    }

    public void setData(List<Recipe> recipes){
        Logger.v(TAG, "setData() invoked");
        mRecipes = recipes;
    }

    static final class ShopHolder extends RecyclerView.ViewHolder{

        ShopRecipeView mShopRecipeView;

        public ShopHolder(View itemView) {
            super(itemView);
            mShopRecipeView = (ShopRecipeView) itemView;
        }
    }
}
