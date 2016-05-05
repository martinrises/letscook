package com.lzf.letscook.system;

import android.os.Handler;
import android.os.Message;

import com.lzf.letscook.db.contract.DbApi;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.net.NetApi;
import com.lzf.letscook.util.Utils;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by liuzhaofeng on 16/4/30.
 */
public class CookSystem {

    private static CookSystem sInstance;
    private Handler mHandler = new CookHandler();

    public static CookSystem getInstance() {
        if (sInstance == null) {
            sInstance = new CookSystem();
        }
        return sInstance;
    }

    private CookSystem() {
    }

    public List<Recipe> getRecipes(String query, String order, int start, int size) throws ExecutionException, InterruptedException {

        // 异步查询数据库；
        List<Recipe> recipes = DbApi.getRecipes(query, order, start, size);
        if(!Utils.isCollectionEmpty(recipes)){
            return recipes;
        }

        // 没有就访问网络
        recipes = NetApi.getRecipesOnline(query, order, start, size);

        // 将网络数据存入DB
        DbApi.writeRecipes(query, order, recipes);

        return recipes;
    }

    private static final class CookHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

        }
    }
}
