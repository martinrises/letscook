package com.lzf.letscook.db;

import android.os.AsyncTask;

import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.net.SubscriberHolder;
import com.lzf.letscook.util.Utils;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.Subscriber;

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

    public static Observable<List<Recipe>> getRecipes(final String query, final String order, final int start, final int size){

        final SubscriberHolder holder = new SubscriberHolder<>();
        Observable<List<Recipe>> ob = Observable.create(new Observable.OnSubscribe<List<Recipe>>() {
            @Override
            public void call(Subscriber<? super List<Recipe>> subscriber) {
                holder.setSubscriber(subscriber);
            }
        });

        new AsyncTask<Void, Void, List<Recipe>>(){

            @Override
            protected List<Recipe> doInBackground(Void... params) {
                List<Recipe> recipes = RecipeDao.getInstance().getRecipes(query, order, start, size);

                return recipes;
            }

            @Override
            protected void onPostExecute(List<Recipe> recipes) {

                Subscriber subscriber = holder.getSubscriber();
                if(subscriber != null){
                    subscriber.onNext(recipes);
                }
            }
        }.executeOnExecutor(EXECUTOR);

        return ob;
    }

    public static void writeRecipes(final String queryTag, final String order, final int start, final int size, final List<Recipe> recipes) {
        if(Utils.isCollectionEmpty(recipes)){
            return;
        }

        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {

                RecipeDao.getInstance().writeRecipes(queryTag, order, start, size, recipes);
                return null;
            }
        }.executeOnExecutor(EXECUTOR);
    }

    public static Observable<Integer> clearRecipes(final String queryTag, final String order) {

        Observable.OnSubscribe<Integer> onSubscribe = new Observable.OnSubscribe<Integer>() {

            @Override
            public void call(final Subscriber<? super Integer> subscriber) {
                new AsyncTask<Void, Void, Integer>() {

                    @Override
                    protected Integer doInBackground(Void... params) {

                        return RecipeDao.getInstance().clearRecipes(queryTag, order);
                    }

                    @Override
                    protected void onPostExecute(Integer integer) {
                        subscriber.onNext(integer);
                    }
                }.executeOnExecutor(EXECUTOR);
            }
        };

        return Observable.create(onSubscribe);

    }

    public static Observable<Boolean> addFavorite(final String recipeId){
        final SubscriberHolder holder = new SubscriberHolder<>();
        Observable<Boolean> ob = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                holder.setSubscriber(subscriber);
            }
        });

        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {

                RecipeDao.getInstance().addFavorite(recipeId);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                holder.getSubscriber().onNext(Boolean.TRUE);
            }
        }.executeOnExecutor(EXECUTOR);

        return ob;
    }

    public static Observable<Boolean> removeFavorite(final String recipeId){
        final SubscriberHolder holder = new SubscriberHolder<>();
        Observable<Boolean> ob = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                holder.setSubscriber(subscriber);
            }
        });

        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {

                RecipeDao.getInstance().removeFavorite(recipeId);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                holder.getSubscriber().onNext(Boolean.TRUE);
            }
        }.executeOnExecutor(EXECUTOR);

        return ob;
    }

    public static Observable<List<Recipe>> getFavRecipes(final int start, final int size){

        final SubscriberHolder holder = new SubscriberHolder<>();
        Observable<List<Recipe>> ob = Observable.create(new Observable.OnSubscribe<List<Recipe>>() {
            @Override
            public void call(Subscriber<? super List<Recipe>> subscriber) {
                holder.setSubscriber(subscriber);
            }
        });

        new AsyncTask<Void, Void, List<Recipe>>(){

            @Override
            protected List<Recipe> doInBackground(Void... params) {
                List<Recipe> recipes = RecipeDao.getInstance().getFavoriteRecipes(start, size);
                return recipes;
            }

            @Override
            protected void onPostExecute(List<Recipe> recipes) {

                Subscriber subscriber = holder.getSubscriber();
                if(subscriber != null){
                    subscriber.onNext(recipes);
                }
            }
        }.executeOnExecutor(EXECUTOR);

        return ob;
    }

    public static Observable<List<Recipe>> getShopRecipes(){

        final SubscriberHolder holder = new SubscriberHolder<>();
        Observable<List<Recipe>> ob = Observable.create(new Observable.OnSubscribe<List<Recipe>>() {
            @Override
            public void call(Subscriber<? super List<Recipe>> subscriber) {
                holder.setSubscriber(subscriber);
            }
        });

        new AsyncTask<Void, Void, List<Recipe>>(){

            @Override
            protected List<Recipe> doInBackground(Void... params) {
                List<Recipe> recipes = RecipeDao.getInstance().getShopRecipes();
                return recipes;
            }

            @Override
            protected void onPostExecute(List<Recipe> recipes) {

                Subscriber subscriber = holder.getSubscriber();
                if(subscriber != null){
                    subscriber.onNext(recipes);
                }
            }
        }.executeOnExecutor(EXECUTOR);

        return ob;
    }

    public static Observable<Boolean> addShopRecipe(final String recipeId) {
        final SubscriberHolder holder = new SubscriberHolder<>();
        Observable<Boolean> ob = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                holder.setSubscriber(subscriber);
            }
        });

        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {

                RecipeDao.getInstance().addShop(recipeId);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                holder.getSubscriber().onNext(Boolean.TRUE);
            }
        }.executeOnExecutor(EXECUTOR);

        return ob;
    }

    public static Observable<Boolean> removeShopRecipe(final String recipeId){

        final SubscriberHolder holder = new SubscriberHolder<>();
        Observable<Boolean> ob = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                holder.setSubscriber(subscriber);
            }
        });

        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {

                RecipeDao.getInstance().removeShop(recipeId);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                holder.getSubscriber().onNext(Boolean.TRUE);
            }

        }.executeOnExecutor(EXECUTOR);

        return ob;
    }

    public static Observable<Boolean> buyMaterial(final String materialId, final boolean isMajor){
        final SubscriberHolder holder = new SubscriberHolder<>();
        Observable<Boolean> ob = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                holder.setSubscriber(subscriber);
            }
        });

        new AsyncTask<Void, Void, Boolean>(){

            @Override
            protected Boolean doInBackground(Void... params) {
                return RecipeDao.getInstance().buyMaterial(materialId, isMajor);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                holder.getSubscriber().onNext(aBoolean);
            }
        }.executeOnExecutor(EXECUTOR);

        return ob;
    }

    public static Observable<Boolean> unBuyMaterial(final String materialId, final boolean isMajor){
        final SubscriberHolder holder = new SubscriberHolder<>();
        Observable<Boolean> ob = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                holder.setSubscriber(subscriber);
            }
        });

        new AsyncTask<Void, Void, Boolean>(){

            @Override
            protected Boolean doInBackground(Void... params) {
                return RecipeDao.getInstance().unBuyMaterial(materialId, isMajor);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                holder.getSubscriber().onNext(aBoolean);
            }
        }.executeOnExecutor(EXECUTOR);

        return ob;
    }

    public static Observable<Boolean> isFavRecipe(final String recipeId) {
        final SubscriberHolder holder = new SubscriberHolder<>();
        Observable<Boolean> ob = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                holder.setSubscriber(subscriber);
            }
        });

        new AsyncTask<Void, Void, Boolean>(){

            @Override
            protected Boolean doInBackground(Void... params) {
                return RecipeDao.getInstance().isFavorite(recipeId);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                holder.getSubscriber().onNext(aBoolean);
            }
        }.executeOnExecutor(EXECUTOR);

        return ob;
    }
}
