package com.lzf.letscook.system.fav;

import com.lzf.letscook.db.DbApi;
import com.lzf.letscook.entity.Recipe;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by liuzhaofeng on 16/6/7.
 */
public class FavSystem {

    private static FavSystem sInstance;

    private final ArrayList<OnFavChangeListener> mOnFavChangeListeners = new ArrayList<>();

    private FavSystem(){
    }

    public static FavSystem getInstance(){
        if(sInstance == null){
            sInstance = new FavSystem();
        }
        return sInstance;
    }

    public Observable<Boolean> addFavorite(final String recipeId){
        return DbApi.addFavorite(recipeId).flatMap(new Func1<Boolean, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(Boolean aBoolean) {
                notifyOnLike(recipeId);
                return Observable.just(aBoolean);
            }
        });
    }

    public Observable<Boolean> removeFavorite(final String recipeId){
        return DbApi.removeFavorite(recipeId).flatMap(new Func1<Boolean, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(Boolean aBoolean) {
                notifyOnDislike(recipeId);
                return Observable.just(aBoolean);
            }
        });
    }

    public Observable<List<Recipe>> getFavorite(int start, int size) {
        return DbApi.getFavRecipes(start, size);
    }

    public void addOnFavListener(OnFavChangeListener listener) {
        mOnFavChangeListeners.add(listener);
    }

    private void notifyOnLike(String recipeId){
        notifyOnFavChange(recipeId, true);
    }

    private void notifyOnDislike(String recipeId){
        notifyOnFavChange(recipeId, false);
    }

    private void notifyOnFavChange(String recipeId, boolean isFav) {
        for(OnFavChangeListener l : mOnFavChangeListeners){
            l.onFavChanged(recipeId, isFav);
        }
    }
}
