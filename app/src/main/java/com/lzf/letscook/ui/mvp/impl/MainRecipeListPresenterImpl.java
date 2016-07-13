package com.lzf.letscook.ui.mvp.impl;

import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.system.CookSystem;
import com.lzf.letscook.ui.mvp.contract.RecipeListView;

import java.util.List;

import rx.Observable;

/**
 * Created by liuzhaofeng on 16/6/9.
 */
public class MainRecipeListPresenterImpl extends BaseRecipeListPresenterImpl {
    public MainRecipeListPresenterImpl(RecipeListView view, String type, String order) {
        super(view, type, order);
    }

    @Override
    public Observable<List<Recipe>> getRecipes() {
        return CookSystem.getInstance().getRecipes(mTag, mOrder, mCursor, PAGE_SIZE);
    }
}
