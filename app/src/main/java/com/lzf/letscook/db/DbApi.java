package com.lzf.letscook.db;

import android.os.AsyncTask;

import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.net.SubscriberHolder;
import com.lzf.letscook.util.Utils;

import java.util.ArrayList;
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

    public static void writeRecipes(final String query, final String order, final int start, final int size, final List<Recipe> recipes) {
        if(Utils.isCollectionEmpty(recipes)){
            return;
        }

        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {

                RecipeDao.getInstance().writeRecipes(query, order, start, size, recipes);
                return null;
            }
        }.executeOnExecutor(EXECUTOR);
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
                holder.getSubscriber().onNext(Boolean.TRUE);
                return null;
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
                holder.getSubscriber().onNext(Boolean.TRUE);
                return null;
            }
        }.executeOnExecutor(EXECUTOR);

        return ob;
    }

    public static Observable<ArrayList<Recipe>> getFavRecipes(){

        final SubscriberHolder holder = new SubscriberHolder<>();
        Observable<ArrayList<Recipe>> ob = Observable.create(new Observable.OnSubscribe<ArrayList<Recipe>>() {
            @Override
            public void call(Subscriber<? super ArrayList<Recipe>> subscriber) {
                holder.setSubscriber(subscriber);
            }
        });

        new AsyncTask<Void, Void, List<Recipe>>(){

            @Override
            protected List<Recipe> doInBackground(Void... params) {
                List<Recipe> recipes = RecipeDao.getInstance().getFavoriteRecipes();
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

    public static Observable<ArrayList<Recipe>> getShopRecipes(){

        final SubscriberHolder holder = new SubscriberHolder<>();
        Observable<ArrayList<Recipe>> ob = Observable.create(new Observable.OnSubscribe<ArrayList<Recipe>>() {
            @Override
            public void call(Subscriber<? super ArrayList<Recipe>> subscriber) {
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
                holder.getSubscriber().onNext(Boolean.TRUE);
                return null;
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

        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {

                holder.getSubscriber().onNext(RecipeDao.getInstance().buyMaterial(materialId, isMajor));
                return null;
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

        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {

                holder.getSubscriber().onNext(RecipeDao.getInstance().unBuyMaterial(materialId, isMajor));
                return null;
            }
        }.executeOnExecutor(EXECUTOR);

        return ob;
    }
}
