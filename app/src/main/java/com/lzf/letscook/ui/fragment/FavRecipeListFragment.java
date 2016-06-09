package com.lzf.letscook.ui.fragment;

import com.lzf.letscook.ui.mvp.contract.RecipeListPresenter;
import com.lzf.letscook.ui.mvp.impl.FavRecipeListPresenterImpl;

/**
 * Created by liuzhaofeng on 16/6/9.
 */
public class FavRecipeListFragment extends BaseRecipeListFragment {
    @Override
    protected RecipeListPresenter onCreatePresenter() {
        return new FavRecipeListPresenterImpl(this, "减肥食谱", "1");
    }
}
