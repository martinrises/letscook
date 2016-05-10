package com.lzf.letscook.db.future;

import com.lzf.letscook.entity.Recipe;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by liuzhaofeng on 16/5/4.
 */
public final class RecipesFuture implements Future<List<Recipe>> {

    public List<Recipe> recipes;

    public void setRecipes(List<Recipe> recipes){
        this.recipes = recipes;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public List<Recipe> get() throws ExecutionException, InterruptedException {
        return doGet();
    }

    private synchronized List<Recipe> doGet() throws InterruptedException, ExecutionException {
        wait(0);
        return recipes;
    }

    @Override
    public List<Recipe> get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    public synchronized void release() {
        notifyAll();
    }
}