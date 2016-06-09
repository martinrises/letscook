package com.lzf.letscook.system;

import com.lzf.letscook.db.DbApi;
import com.lzf.letscook.entity.Recipe;

import java.util.HashSet;
import java.util.List;

import rx.Observable;

/**
 * Created by liuzhaofeng on 16/6/7.
 */
public class FavSystem {

    private static FavSystem sInstance;

    private final HashSet<Recipe> favSet = new HashSet<>();

    private FavSystem(){}

    public static FavSystem getInstance(){
        if(sInstance == null){
            sInstance = new FavSystem();
        }
        return sInstance;
    }

    public Observable<Boolean> addFavorite(String recipeId){
        return DbApi.addFavorite(recipeId);
    }

    public Observable<Boolean> removeFavorite(String recipeId){
        return DbApi.removeFavorite(recipeId);
    }

    public Observable<List<Recipe>> getFavorite(int start, int size) {
        return DbApi.getFavRecipes(start, size);
    }

}
