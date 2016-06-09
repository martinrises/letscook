package com.lzf.letscook.ui.fragment;

import com.lzf.letscook.ui.mvp.contract.RecipeListPresenter;
import com.lzf.letscook.ui.mvp.impl.MainRecipeListPresenterImpl;

/**
 * Created by liuzhaofeng on 16/6/9.
 */
public class MainRecipeListFragment extends BaseRecipeListFragment {
    @Override
    protected RecipeListPresenter onCreatePresenter() {
        return new MainRecipeListPresenterImpl(this, "减肥食谱", "1");
    }
}
