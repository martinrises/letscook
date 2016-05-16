package com.lzf.letscook.ui.mvp.impl;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.system.CookSystem;
import com.lzf.letscook.ui.mvp.contract.RecipeListPresenter;
import com.lzf.letscook.ui.mvp.contract.RecipeListView;
import com.lzf.letscook.util.Logger;
import com.lzf.letscook.util.Utils;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by liuzhaofeng on 16/5/14.
 */
public class RecipeListPresenterImpl extends RecipeListPresenter {

    public static final String TAG = RecipeListPresenterImpl.class.getSimpleName();

    public static final int PAGE_SIZE = 10; // 分页查询，一页的大小
    public static final int BUFFER_SIZE = 5; // 还剩多少开始查询

    private RecipeListView mView;
    private String mType; // 查询类别 “减肥食谱”
    private String mOrder; // 查询次序 “1”
    private int cursor; // 分页开始的游标

    private boolean isLoading;
    private boolean isRefreshing;

    public RecipeListPresenterImpl(RecipeListView view, String type, String order) {
        this.mView = view;
        this.mType = type;
        this.mOrder = order;
    }

    @Override
    public void onRefresh() {

        // 停止loading的动画，标志位置为false
        mView.stopLoad();
        isLoading = false;

        // 开启refresh的动画，重新获取recipes
        cursor = 0;
        isRefreshing = true;
        Observable<List<Recipe>> recipesOb = CookSystem.getInstance().getRecipes(mType, mOrder, cursor, PAGE_SIZE);
        recipesOb.subscribe(new Action1<List<Recipe>>() {
            @Override
            public void call(List<Recipe> recipes) {
                isRefreshing = false;
                mView.onSetRecipes(recipes);
                mView.stopFresh();
            }
        });
        cursor += PAGE_SIZE;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager != null && layoutManager instanceof LinearLayoutManager) {
            int itemCount = layoutManager.getItemCount();

            int lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            if (itemCount - lastPosition < BUFFER_SIZE && !isRefreshing && !isLoading) {

                Logger.v("test", "itemCount = " + itemCount + ";  lastPosition = " + lastPosition + ";  isRefreshing = " + isRefreshing + ";  isLoading = " + isLoading);
                isLoading = true;
                mView.startLoad();

                loadMore();
            }
        }
    }

    private void loadMore() {
        Observable<List<Recipe>> recipesOb = CookSystem.getInstance().getRecipes(mType, mOrder, cursor, PAGE_SIZE);
        recipesOb.filter(new Func1<List<Recipe>, Boolean>() {
            @Override
            public Boolean call(List<Recipe> recipes) {
                return !isRefreshing && isLoading;
            }
        }).subscribe(new Action1<List<Recipe>>() {
            @Override
            public void call(List<Recipe> recipes) {

                if (!Utils.isCollectionEmpty(recipes)) {
                    cursor += recipes.size();
                    Logger.v(TAG, cursor + " >>> " + recipes.get(0).getCook_id());
                    mView.onAppendRecipes(recipes);
                }
                isLoading = false;
                mView.stopLoad();
            }
        });
    }
}
