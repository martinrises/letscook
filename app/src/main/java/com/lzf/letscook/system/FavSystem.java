package com.lzf.letscook.system;

import com.lzf.letscook.db.DbApi;
import com.lzf.letscook.entity.Recipe;

import java.util.ArrayList;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by liuzhaofeng on 16/6/7.
 */
public class FavSystem {

    private static FavSystem sInstance;

    private final ArrayList<Recipe> favList = new ArrayList<>();

    private FavSystem(){}

    public static FavSystem getInstance(){
        if(sInstance == null){
            sInstance = new FavSystem();
        }
        return sInstance;
    }


    public void init(){

        DbApi.getFavRecipes().subscribe(new Action1<ArrayList<Recipe>>() {
            @Override
            public void call(ArrayList<Recipe> recipes) {
                favList.addAll(recipes);
            }
        });
    }

    public Observable<Boolean> addFavorite(String recipeId){
        return DbApi.addFavorite(recipeId);
    }

    public Observable<Boolean> removeFavorite(String recipeId){
        return DbApi.removeFavorite(recipeId);
    }

}
