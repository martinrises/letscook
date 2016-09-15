package com.lzf.letscook.ui.fragment;

import android.os.Bundle;

import com.lzf.letscook.R;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.system.fav.FavSystem;
import com.lzf.letscook.ui.mvp.contract.RecipeListPresenter;
import com.lzf.letscook.ui.mvp.impl.FavRecipeListPresenterImpl;
import com.lzf.letscook.util.Utils;

import java.util.List;

/**
 * Created by liuzhaofeng on 16/6/9.
 */
public class FavRecipeListFragment extends BaseRecipeListFragment {

    public static final String FAV_TIP_FRAGMENT_TAG = "favTipFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FavSystem.getInstance().addOnFavListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FavSystem.getInstance().removeOnFavListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recipe_list_favorite;
    }

    @Override
    protected RecipeListPresenter onCreatePresenter() {
        return new FavRecipeListPresenterImpl(this, "收藏", "1");
    }



    @Override
    public void onFavChanged(String recipeId, boolean isFav) {
        mPresenter.onRefresh();
    }

    @Override
    public void onSetRecipes(List<Recipe> recipes) {
        super.onSetRecipes(recipes);

        if(Utils.isCollectionEmpty(recipes)){
            EmptyTipsFragment.showEmptyTips(R.string.tip_fav_empty, 0, getFragmentManager(), R.id.base_frag_root_fav, FAV_TIP_FRAGMENT_TAG);
        } else {
            EmptyTipsFragment.hideEmptyTips(getFragmentManager(), FAV_TIP_FRAGMENT_TAG);
        }
    }
}
