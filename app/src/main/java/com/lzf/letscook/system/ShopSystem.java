package com.lzf.letscook.system;

import com.lzf.letscook.db.DbApi;
import com.lzf.letscook.entity.Recipe;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by liuzhaofeng on 16/6/7.
 */
public class ShopSystem {

    private static ShopSystem sInstance;

    private static ShopSystem getInstance(){
        if(sInstance == null){
            sInstance = new ShopSystem();
        }

        return sInstance;
    }

    private ShopSystem(){}

    public Observable<ArrayList<Recipe>> getShopRecipes(){
        return DbApi.getShopRecipes();
    }


}
