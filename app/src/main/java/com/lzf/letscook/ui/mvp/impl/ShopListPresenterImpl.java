package com.lzf.letscook.ui.mvp.impl;

import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.system.shop.ShopSystem;
import com.lzf.letscook.ui.mvp.contract.ShopListPresenter;
import com.lzf.letscook.ui.mvp.contract.ShopListView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by liuzhaofeng on 16/6/14.
 */
public class ShopListPresenterImpl implements ShopListPresenter {

    private ShopListView mView;

    private Subscriber mRefreshSub;

    public ShopListPresenterImpl(ShopListView view){
        this.mView = view;

        initRefreshOb();
    }

    private void initRefreshOb() {

        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                mRefreshSub = subscriber;
            }
        }).map(new Func1<Object, Object>() {
            @Override
            public Object call(Object aVoid) {
                mView.onStartLoad();
                return aVoid;
            }
        }).flatMap(new Func1<Object, Observable<List<Recipe>>>() {
            @Override
            public Observable<List<Recipe>> call(Object aVoid) {
                return ShopSystem.getInstance().getShopRecipes();
            }
        }).subscribe(new Action1<List<Recipe>>() {
            @Override
            public void call(List<Recipe> recipes) {
                mView.onLoadComplete(recipes);
            }
        });
    }


    @Override
    public void startLoad() {
        mRefreshSub.onNext(new Object());
    }
}
