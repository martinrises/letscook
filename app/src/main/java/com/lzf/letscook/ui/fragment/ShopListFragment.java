package com.lzf.letscook.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzf.letscook.R;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.ui.adapter.ShopAdapter;
import com.lzf.letscook.ui.mvp.contract.ShopListPresenter;
import com.lzf.letscook.ui.mvp.contract.ShopListView;
import com.lzf.letscook.ui.mvp.impl.ShopListPresenterImpl;
import com.lzf.letscook.ui.view.ProgressDialogFragment;

import java.util.List;

/**
 * Created by liuzhaofeng on 16/6/12.
 */
public class ShopListFragment extends BaseFragment implements ShopListView {

    private RecyclerView mShopRv;
    private ShopAdapter mAdapter;

    private ProgressDialogFragment mPd;
    private ShopListPresenter mPresenter;

    public ShopListFragment(){
        mPresenter = new ShopListPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mShopRv = (RecyclerView) view.findViewById(R.id.recipe_list);
        LinearLayoutManager llm = new LinearLayoutManager(mContext) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return super.generateDefaultLayoutParams();
            }

            @Override
            public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
                return super.generateLayoutParams(lp);
            }

            @Override
            public RecyclerView.LayoutParams generateLayoutParams(Context c, AttributeSet attrs) {
                return super.generateLayoutParams(c, attrs);
            }
        };
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mShopRv.setLayoutManager(llm);
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
    }
}
