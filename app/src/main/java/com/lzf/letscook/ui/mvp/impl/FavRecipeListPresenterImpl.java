package com.lzf.letscook.ui.mvp.impl;

import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.system.fav.FavSystem;
import com.lzf.letscook.ui.mvp.contract.RecipeListView;

import java.util.List;

import rx.Observable;

/**
 * Created by liuzhaofeng on 16/6/9.
 */
public class FavRecipeListPresenterImpl extends BaseRecipeListPresenterImpl {

    public FavRecipeListPresenterImpl(RecipeListView view, String type, String order) {
        super(view, type, order);
    }

    @Override
    protected void beforeRefresh() {
        // TODO nothing
    }

    @Override
    public Observable<List<Recipe>> getRecipes() {
        return FavSystem.getInstance().getFavorite(mCursor, PAGE_SIZE);
    }
}
