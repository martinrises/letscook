package com.lzf.letscook.ui.mvp.contract;

import com.lzf.letscook.entity.Recipe;

import java.util.List;

/**
 * Created by liuzhaofeng on 16/5/14.
 */
public interface RecipeListView {

    void onSetRecipes(List<Recipe> recipes);

    void onAppendRecipes(List<Recipe> recipes);

    void stopFresh();

    void startLoad();

    void stopLoad();
}
