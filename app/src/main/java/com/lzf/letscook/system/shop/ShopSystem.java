package com.lzf.letscook.system.shop;

import com.lzf.letscook.db.DbApi;
import com.lzf.letscook.entity.Recipe;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by liuzhaofeng on 16/6/7.
 */
public class ShopSystem {

    private static ShopSystem sInstance;

    private final ArrayList<OnShopChangeListener> mOnShopChangeListeners = new ArrayList<>();

    public static ShopSystem getInstance() {
        if (sInstance == null) {
            sInstance = new ShopSystem();
        }

        return sInstance;
    }

    private ShopSystem() {
    }

    public Observable<List<Recipe>> getShopRecipes() {
        return DbApi.getShopRecipes();
    }

    public Observable<Boolean> addShopRecipe(final String recipeId) {

        return DbApi.addShopRecipe(recipeId).flatMap(new Func1<Boolean, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(Boolean aBoolean) {
                notifyOnShop(recipeId);
                return Observable.just(aBoolean);

            }
        });
    }

    public Observable<Boolean> removeShopRecipe(final String recipeId) {
        return DbApi.removeShopRecipe(recipeId).flatMap(new Func1<Boolean, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(Boolean aBoolean) {
                notifyOnDisShop(recipeId);
                return Observable.just(aBoolean);
            }
        });
    }

    public Observable<Boolean> buyMaterial(String _id, boolean isMajor) {
        return DbApi.buyMaterial(_id, isMajor);
    }

    public Observable<Boolean> unbuyMaterial(String _id, boolean isMajor) {
        return DbApi.unBuyMaterial(_id, isMajor);
    }

    public void addOnShopListener(OnShopChangeListener listener) {
        mOnShopChangeListeners.add(listener);
    }

    private void notifyOnShop(String recipeId) {
        notifyOnShopChange(recipeId, true);
    }

    private void notifyOnDisShop(String recipeId) {
        notifyOnShopChange(recipeId, false);
    }

    private void notifyOnShopChange(String recipeId, boolean isShop) {
        for (OnShopChangeListener l : mOnShopChangeListeners) {
            l.onShopChanged(recipeId, isShop);
        }
    }

    public void removeOnShopListener(OnShopChangeListener listener) {
        mOnShopChangeListeners.remove(listener);
    }
}
