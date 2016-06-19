package com.lzf.letscook.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lzf.letscook.R;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.ui.mvp.contract.ShopListPresenter;
import com.lzf.letscook.ui.mvp.contract.ShopListView;
import com.lzf.letscook.ui.mvp.impl.ShopListPresenterImpl;
import com.lzf.letscook.ui.view.ProgressDialogFragment;
import com.lzf.letscook.ui.view.ShopRecipeView;
import com.lzf.letscook.util.Utils;

import java.util.List;

/**
 * Created by liuzhaofeng on 16/6/12.
 */
public class ShopListFragment extends BaseFragment implements ShopListView {

    private LinearLayout mContainerLl;
    private LinearLayout.LayoutParams mLp;
    private ProgressDialogFragment mPd;
    private ShopListPresenter mPresenter;

    public ShopListFragment(){
        mPresenter = new ShopListPresenterImpl(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int marginLR = (int) getResources().getDimension(R.dimen.margin_recipe_shop_card_left_right);
        int marginTB = (int) getResources().getDimension(R.dimen.margin_recipe_shop_card_top_bottom);
        mLp.setMargins(marginLR, marginTB, marginLR, marginTB);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mContainerLl = (LinearLayout) view.findViewById(R.id.shop_container_ll);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.startLoad();
    }

    @Override
    public void onStartLoad() {
        if(mPd == null){
            mPd = new ProgressDialogFragment();
        }
        mPd.showPd(getFragmentManager(), R.string.shop_loading);
    }

    @Override
    public void onLoadComplete(List<Recipe> recipes) {
        mPd.dismiss();

        if(!Utils.isCollectionEmpty(recipes)){

            mContainerLl.removeAllViews();

            for(Recipe recipe : recipes){
                ShopRecipeView item = new ShopRecipeView(mContext);
                item.setRecipe(recipe);
                mContainerLl.addView(item, mLp);
            }
        }
    }
}
