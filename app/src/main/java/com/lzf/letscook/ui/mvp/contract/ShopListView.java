package com.lzf.letscook.ui.mvp.contract;

import com.lzf.letscook.entity.Recipe;

import java.util.List;

/**
 * Created by liuzhaofeng on 16/6/14.
 */
public interface ShopListView {

    void onStartLoad();

    void onLoadComplete(List<Recipe> recipes);
}
