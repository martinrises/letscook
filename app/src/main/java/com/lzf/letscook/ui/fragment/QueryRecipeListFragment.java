package com.lzf.letscook.ui.fragment;

import android.text.TextUtils;
import android.widget.Toast;

import com.lzf.letscook.LetsCook;
import com.lzf.letscook.R;
import com.lzf.letscook.ui.mvp.contract.RecipeListPresenter;
import com.lzf.letscook.ui.mvp.impl.QueryRecipeListPresenterImpl;
import com.lzf.letscook.util.ToastManager;

/**
 * Created by liuzhaofeng on 16/7/13.
 */
public class QueryRecipeListFragment extends BaseRecipeListFragment {

    @Override
    protected RecipeListPresenter onCreatePresenter() {
        return new QueryRecipeListPresenterImpl(this, "减肥食谱|健康食谱|美容食谱", "2");
    }

    public void onSearch(String keyword) {
        if(TextUtils.isEmpty(keyword) || TextUtils.isEmpty(keyword.trim())){
            ToastManager.makeTextAndShow(LetsCook.getApp().getApplicationContext(), R.string.tip_query_empty, Toast.LENGTH_SHORT);
            return;
        }

        ((QueryRecipeListPresenterImpl)mPresenter).setKeyword(keyword);
        mPresenter.onRefresh();
    }
}
