package com.lzf.letscook.ui.mvp.impl;

import android.widget.Toast;

import com.lzf.letscook.LetsCook;
import com.lzf.letscook.R;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.system.CookSystem;
import com.lzf.letscook.ui.mvp.contract.RecipeListView;
import com.lzf.letscook.util.ToastManager;
import com.lzf.letscook.util.Utils;

import java.util.List;

import rx.Observable;

/**
 * Created by liuzhaofeng on 16/7/13.
 */
public class QueryRecipeListPresenterImpl extends BaseRecipeListPresenterImpl {

    private String mKeyword;

    public QueryRecipeListPresenterImpl(RecipeListView view, String type, String order) {
        super(view, type, order);
    }

    @Override
    public Observable<List<Recipe>> getRecipes(boolean isRefresh) {
        return CookSystem.getInstance().getRecipesSearch(mKeyword, mTag, mCursor, PAGE_SIZE);
    }

    @Override
    protected void onRecipesLoad(List<Recipe> recipes) {
        if(Utils.isCollectionEmpty(recipes)){
            ToastManager.makeTextAndShow(LetsCook.getApp(), R.string.no_related_recipe, Toast.LENGTH_SHORT);
        }
    }

    public void setKeyword(String keyword){
        this.mKeyword = keyword;
    }
}
