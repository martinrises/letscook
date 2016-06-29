package com.lzf.letscook.ui.fragment;

import com.lzf.letscook.system.fav.FavSystem;
import com.lzf.letscook.system.fav.OnFavChangeListener;
import com.lzf.letscook.ui.mvp.contract.RecipeListPresenter;
import com.lzf.letscook.ui.mvp.impl.FavRecipeListPresenterImpl;

/**
 * Created by liuzhaofeng on 16/6/9.
 */
public class FavRecipeListFragment extends BaseRecipeListFragment implements OnFavChangeListener {

    public FavRecipeListFragment(){
        FavSystem.getInstance().addOnFavListener(this);
    }

    @Override
    protected RecipeListPresenter onCreatePresenter() {
        return new FavRecipeListPresenterImpl(this, "收藏", "1");
    }

    @Override
    public void onFavChanged(String recipeId, boolean isFav) {
        mPresenter.onRefresh();
    }
}
