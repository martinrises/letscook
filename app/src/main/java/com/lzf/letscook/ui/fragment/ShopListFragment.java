package com.lzf.letscook.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzf.letscook.R;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.system.shop.OnShopChangeListener;
import com.lzf.letscook.system.shop.ShopSystem;
import com.lzf.letscook.ui.adapter.ShopAdapter;
import com.lzf.letscook.ui.mvp.contract.ShopListPresenter;
import com.lzf.letscook.ui.mvp.contract.ShopListView;
import com.lzf.letscook.ui.mvp.impl.ShopListPresenterImpl;
import com.lzf.letscook.ui.view.ProgressDialogFragment;
import com.lzf.letscook.util.Utils;

import java.util.List;

/**
 * Created by liuzhaofeng on 16/6/12.
 */
public class ShopListFragment extends BaseFragment implements ShopListView, OnShopChangeListener {

    public static final String SHOP_TIP_FRAGMENT_TAG = "shopTipFragment";

    private RecyclerView mShopRv;
    private ShopAdapter mAdapter;

    private ProgressDialogFragment mPd;
    private ShopListPresenter mPresenter = new ShopListPresenterImpl(this);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShopSystem.getInstance().addOnShopListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ShopSystem.getInstance().removeOnShopListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mShopRv = (RecyclerView) view.findViewById(R.id.recipe_list);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mShopRv.setLayoutManager(llm);

        mShopRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.margin_vertical_shop));
            }
        });

        mAdapter = new ShopAdapter();
        mShopRv.setAdapter(mAdapter);
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
        mPd.dismissAllowingStateLoss();

        mAdapter.setData(recipes);
        mAdapter.notifyDataSetChanged();

        if(Utils.isCollectionEmpty(recipes)){
            EmptyTipsFragment.showEmptyTips(R.string.tip_shop_empty, 0, getFragmentManager(), R.id.base_frag_root_shop, SHOP_TIP_FRAGMENT_TAG);
        }
    }

    @Override
    public void onShopChanged(String recipeId, boolean isShop) {
        mPresenter.startLoad();
    }
}
