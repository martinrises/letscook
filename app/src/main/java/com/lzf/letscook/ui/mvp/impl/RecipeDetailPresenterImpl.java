package com.lzf.letscook.ui.mvp.impl;

import com.lzf.letscook.ui.mvp.contract.RecipeDetailPresenter;
import com.lzf.letscook.ui.mvp.contract.RecipeDetailView;

import rx.Subscriber;

/**
 * Created by asus on 2016/5/27.
 */
public class RecipeDetailPresenterImpl implements RecipeDetailPresenter{

    private RecipeDetailView mView;

    private Subscriber<? super String> mFavClickSubcriber;

    public RecipeDetailPresenterImpl(RecipeDetailView view){
        this.mView = view;

        init();
    }

    private void init() {
    }

    @Override
    public void like(String recipeId) {

    }

    @Override
    public void dislike(String recipeId) {

    }

    @Override
    public void share(String recipeId) {

    }

    @Override
    public void addToShopList(String recipeId) {

    }

    @Override
    public void removeFromShopList(String recipeId) {

    }

    @Override
    public void checkAndChangeLikeStatus(String recipeId) {

    }
}
