package com.lzf.letscook.db.contract;

import android.os.AsyncTask;

import com.lzf.letscook.db.RecipeDao;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.net.future.RecipesFuture;
import com.lzf.letscook.util.Utils;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liuzhaofeng on 16/5/4.
 */
public class DbApi {

    private static final int CORE_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int MIN_POOL_SIZE = 1;
    private static final int MAX_POOL_SIZE = 2 * CORE_COUNT + 1;
    private static final int KEEP_ALIVE = 1;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
        }
    };
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(128);
    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(MIN_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, sPoolWorkQueue);

    public static List<Recipe> getRecipes(final String query, final String order, final int start, final int size) throws ExecutionException, InterruptedException {

        final RecipesFuture future = new RecipesFuture();

        new AsyncTask<Void, Void, List<Recipe>>(){

            @Override
            protected List<Recipe> doInBackground(Void... params) {
                return RecipeDao.getInstance().getRecipes(query, order, start, size);
            }

            @Override
            protected void onPostExecute(List<Recipe> recipes) {
                future.setRecipes(recipes);
                future.notifyAll();
            }
        }.executeOnExecutor(EXECUTOR);

        return future.get();
    }

    public static void writeRecipes(final String query, final String order, final List<Recipe> recipes) {
        if(Utils.isCollectionEmpty(recipes)){
            return;
        }

        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {

                RecipeDao.getInstance().writeRecipes(query, order, recipes);
                return null;
            }
        }.executeOnExecutor(EXECUTOR);
    }
}
