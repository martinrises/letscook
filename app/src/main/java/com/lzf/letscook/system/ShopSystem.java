package com.lzf.letscook.system;

import com.lzf.letscook.db.DbApi;
import com.lzf.letscook.entity.Recipe;

import java.util.List;

import rx.Observable;

/**
 * Created by liuzhaofeng on 16/6/7.
 */
public class ShopSystem {

    private static ShopSystem sInstance;

    public static ShopSystem getInstance(){
        if(sInstance == null){
            sInstance = new ShopSystem();
        }

        return sInstance;
    }

    private ShopSystem(){}

    public Observable<List<Recipe>> getShopRecipes(){
        return DbApi.getShopRecipes();
    }

    public Observable<Boolean> addShopRecipe(String recipeId){
        return DbApi.addShopRecipe(recipeId);
    }

    public Observable<Boolean> removeShopRecipe(String recipeId){
        return DbApi.removeShopRecipe(recipeId);
    }

    public Observable<Boolean> buyMaterial(String _id, boolean isMajor){
        return DbApi.buyMaterial(_id, isMajor);
    }

    public Observable<Boolean> unbuyMaterial(String _id, boolean isMajor){
        return DbApi.unBuyMaterial(_id, isMajor);
    }
}
