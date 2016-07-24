package com.lzf.letscook.ui.mvp.impl;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.lzf.letscook.LetsCook;
import com.lzf.letscook.db.DbApi;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.ui.mvp.contract.RecipeListPresenter;
import com.lzf.letscook.ui.mvp.contract.RecipeListView;
import com.lzf.letscook.util.Logger;
import com.lzf.letscook.util.Utils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by liuzhaofeng on 16/5/14.
 */
public abstract class BaseRecipeListPresenterImpl extends RecipeListPresenter {

    public static final String TAG = BaseRecipeListPresenterImpl.class.getSimpleName();

    public static final int PAGE_SIZE = 10; // 分页查询，一页的大小
    public static final int BUFFER_SIZE = 5; // 还剩多少开始查询

    protected RecipeListView mView;
    protected String mTag; // 查询类别 “减肥食谱”
    protected String mOrder; // 查询次序 “1”
    protected int mCursor; // 分页开始的游标

    private boolean isLoading;
    private boolean isRefreshing;

    private Subscriber<? super RecyclerView> loadMoreSub;
    private Subscriber refreshSub;

    public BaseRecipeListPresenterImpl(RecipeListView view, String tag, String order) {
        this.mView = view;
        this.mTag = tag;
        this.mOrder = order;

        initLoadMoreOb();
        initRefreshOb();
    }

    private void initRefreshOb() {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                refreshSub = subscriber;
            }
        }).map(new Func1<Object, Object>() {
            @Override
            public Object call(Object aVoid) {
                // 停止loading的动画，标志位置为false
                mView.stopLoad();
                isLoading = false;
                mCursor = 0;
                isRefreshing = true;
                return aVoid;
            }
        }).map(new Func1<Object, Object>() {
            @Override
            public Object call(Object o) {
                beforeRefresh();
                return null;
            }
        }).flatMap(new Func1<Object, Observable<List<Recipe>>>() {
            @Override
            public Observable<List<Recipe>> call(Object aVoid) {
                return getRecipes();
            }
        }).subscribe(new Action1<List<Recipe>>() {
            @Override
            public void call(List<Recipe> recipes) {
                isRefreshing = false;
                mView.onSetRecipes(recipes);
                mView.stopFresh();
                mCursor += PAGE_SIZE;
            }
        });
    }

    protected void beforeRefresh() {
        // 清空db
        DbApi.clearRecipes(mTag, mOrder);
    }

    private void initLoadMoreOb() {

        Observable.create(new Observable.OnSubscribe<RecyclerView>() {
            @Override
            public void call(Subscriber<? super RecyclerView> subscriber) {
                loadMoreSub = subscriber;
            }
        }).filter(new Func1<RecyclerView, Boolean>() {
            @Override
            public Boolean call(RecyclerView recyclerView) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager instanceof LinearLayoutManager) {
                    int itemCount = layoutManager.getItemCount();
                    int lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

                    return (itemCount - lastPosition < BUFFER_SIZE && !isRefreshing && !isLoading);
                }
                return false;
            }
        }).map(new Func1<RecyclerView, RecyclerView>() {
            @Override
            public RecyclerView call(RecyclerView recyclerView) {
                isLoading = true;
                mView.startLoad();
                return recyclerView;
            }
        }).flatMap(new Func1<RecyclerView, Observable<List<Recipe>>>() {
            @Override
            public Observable<List<Recipe>> call(RecyclerView recyclerView) {
                return getRecipes();
            }
        }).filter(new Func1<List<Recipe>, Boolean>() {
            @Override
            public Boolean call(List<Recipe> recipes) {
                return !isRefreshing && isLoading;
            }
        }).subscribe(new Action1<List<Recipe>>() {
            @Override
            public void call(List<Recipe> recipes) {

                if (!Utils.isCollectionEmpty(recipes)) {
                    mCursor += recipes.size();
                    Logger.v(TAG, mCursor + " >>> " + recipes.get(0).getCook_id());
                    mView.onAppendRecipes(recipes);
                }
                isLoading = false;
                mView.stopLoad();
            }
        });


    }

    @Override
    public void onRefresh() {

        if(!Utils.hasInternet()){
            Toast.makeText(LetsCook.getApp(), "网络不可用，请联网重试。", Toast.LENGTH_SHORT).show();
            return;
        }

        refreshSub.onNext(new Object());
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        loadMoreSub.onNext(recyclerView);
    }

    public abstract Observable<List<Recipe>> getRecipes();
}
