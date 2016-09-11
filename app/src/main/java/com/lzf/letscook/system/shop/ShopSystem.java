package com.lzf.letscook.system.shop;

import com.lzf.letscook.db.DbApi;
import com.lzf.letscook.entity.Recipe;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by liuzhaofeng on 16/6/7.
 */
public class ShopSystem {

    private static ShopSystem sInstance;

    private final ArrayList<SoftReference<OnShopChangeListener>> mOnShopChangeListenerRefs = new ArrayList<>();

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

    public Observable<Boolean> clearShopRecipes() {
        return DbApi.clearShopRecipes().doOnNext(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                notifyClearShopRecipes();
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
        mOnShopChangeListenerRefs.add(new SoftReference<>(listener));
    }

    private void notifyOnShop(String recipeId) {
        notifyOnShopChange(recipeId, true);
    }

    private void notifyOnDisShop(String recipeId) {
        notifyOnShopChange(recipeId, false);
    }

    private void notifyOnShopChange(String recipeId, boolean isShop) {
        Iterator<SoftReference<OnShopChangeListener>> it = mOnShopChangeListenerRefs.iterator();
        while (it.hasNext()) {
            SoftReference<OnShopChangeListener> ref = it.next();
            OnShopChangeListener l = ref.get();
            if (l != null) {
                l.onShopChanged(recipeId, isShop);
            } else {
                it.remove();
            }
        }
    }

    private void notifyClearShopRecipes() {
        Iterator<SoftReference<OnShopChangeListener>> it = mOnShopChangeListenerRefs.iterator();
        while (it.hasNext()) {
            SoftReference<OnShopChangeListener> ref = it.next();
            OnShopChangeListener l = ref.get();
            if (l != null) {
                l.onShopCleared();
            } else {
                it.remove();
            }
        }
    }

    public void removeOnShopListener(OnShopChangeListener listener) {

        Iterator<SoftReference<OnShopChangeListener>> it = mOnShopChangeListenerRefs.iterator();
        while (it.hasNext()) {
            SoftReference<OnShopChangeListener> ref = it.next();
            OnShopChangeListener l = ref.get();
            if (l != null) {
                if (l.equals(listener)) {
                    it.remove();
                    return;
                }
            } else {
                it.remove();
            }
        }
    }
}
