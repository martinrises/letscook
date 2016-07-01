package com.lzf.letscook.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.lzf.letscook.ui.mvp.contract.RecipeListPresenter;
import com.lzf.letscook.ui.mvp.impl.MainRecipeListPresenterImpl;
import com.lzf.letscook.util.Logger;

/**
 * Created by liuzhaofeng on 16/6/9.
 */
public class RecipeListFragment extends BaseRecipeListFragment {

    public static final String ARG_TYPE = "arg_type";
    public static final String ARG_ORDER = "arg_order";

    private String mType;
    private String mOrder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initArgs(savedInstanceState != null ? savedInstanceState : getArguments());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_TYPE, mType);
        outState.putString(ARG_ORDER, mOrder);
        super.onSaveInstanceState(outState);
    }

    private void initArgs(Bundle args) {
        mType = args.getString(ARG_TYPE);
        mOrder = args.getString(ARG_ORDER);
    }

    @Override
    protected RecipeListPresenter onCreatePresenter() {
        return new MainRecipeListPresenterImpl(this, mType, mOrder);
    }
}
