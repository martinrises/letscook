package com.lzf.letscook.system;

import com.lzf.letscook.db.DbApi;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.net.NetApi;
import com.lzf.letscook.util.Logger;
import com.lzf.letscook.util.Utils;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by liuzhaofeng on 16/4/30.
 */
public class CookSystem {

    private static CookSystem sInstance;

    public static CookSystem getInstance() {
        if (sInstance == null) {
            sInstance = new CookSystem();
        }
        return sInstance;
    }

    private CookSystem() {
    }

    public Observable<List<Recipe>> getRecipes(final String query, final String order, final int start, final int size) {

        // 异步查询数据库；
        return DbApi.getRecipes(query, order, start, size)
                .flatMap(new Func1<List<Recipe>, Observable<List<Recipe>>>() {
                             @Override
                             public Observable<List<Recipe>> call(List<Recipe> recipes) {

                                 Logger.v("test", "DbApi >>>>>>>>>> " + start + "   " + (Utils.isCollectionEmpty(recipes) ? "" : recipes.get(0).getCook_id()));

                                 if (Utils.isCollectionEmpty(recipes)) {

                                     return NetApi.getRecipesOnline(query, order, start, size);
                                 } else {

                                     return Observable.just(recipes);
                                 }
                             }
                         }

                        // 缓存数据
                ).map(new Func1<List<Recipe>, List<Recipe>>() {
                    @Override
                    public List<Recipe> call(List<Recipe> recipes) {

                        Logger.v("test", "write >>> " + start);
                        DbApi.writeRecipes(query, order, start, size, recipes);
                        return recipes;
                    }
                });

    }

}
