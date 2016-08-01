package com.lzf.letscook.ui.mvp.impl;

import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.system.shop.ShopSystem;
import com.lzf.letscook.system.fav.FavSystem;
import com.lzf.letscook.ui.mvp.contract.RecipeDetailPresenter;
import com.lzf.letscook.ui.mvp.contract.RecipeDetailView;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by asus on 2016/5/27.
 */
public class RecipeDetailPresenterImpl implements RecipeDetailPresenter {

    private RecipeDetailView mView;

    private Subscriber<? super Recipe> mFavSubcriber;
    private Subscriber<? super String> mLikeSubcriber;
    private Subscriber<? super String> mDislikeSubcriber;

    private Subscriber<? super Recipe> mShopSubcriber;
    private Subscriber<? super String> mAddShopSubcriber;
    private Subscriber<? super String> mRemoveShopSubcriber;

    public RecipeDetailPresenterImpl(RecipeDetailView view) {
        this.mView = view;

        init();
    }

    private void init() {
        initFavSubcriber();
        initLikeSubcriber();
        initDislikeSubcriber();

        initShopSubscriber();
        initAddShopSubcriber();
        initRemoveShopSubcriber();
    }

    private void initRemoveShopSubcriber() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                mRemoveShopSubcriber = subscriber;
            }
        }).flatMap(new Func1<String, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(String recipeId) {
                return ShopSystem.getInstance().removeShopRecipe(recipeId);
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if(aBoolean){
                    mView.onRemoveToShopList();
                }
            }
        });
    }

    private void initAddShopSubcriber() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                mAddShopSubcriber = subscriber;
            }
        }).flatMap(new Func1<String, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(String recipeId) {
                return ShopSystem.getInstance().addShopRecipe(recipeId);
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if(aBoolean){
                    mView.onAddToShopList();
                }
            }
        });
    }

    private void initShopSubscriber() {
        Observable.create(new Observable.OnSubscribe<Recipe>() {
            @Override
            public void call(Subscriber<? super Recipe> subscriber) {
                mShopSubcriber = subscriber;
            }
        }).subscribe(new Action1<Recipe>() {
            @Override
            public void call(Recipe recipe) {

                if (!recipe.isInShopList()) {
                    addToShopList(recipe.getCook_id());
                } else {
                    removeFromShopList(recipe.getCook_id());
                }
            }
        });
    }

    private void initDislikeSubcriber() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                mDislikeSubcriber = subscriber;
            }
        }).flatMap(new Func1<String, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(String recipeId) {
                return FavSystem.getInstance().removeFavorite(recipeId);
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    mView.onDislike();
                }
            }
        });
    }

    private void initLikeSubcriber() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                mLikeSubcriber = subscriber;
            }
        }).flatMap(new Func1<String, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(String recipeId) {
                return FavSystem.getInstance().addFavorite(recipeId);
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    mView.onLike();
                }
            }
        });
    }

    private void initFavSubcriber() {
        Observable.create(new Observable.OnSubscribe<Recipe>() {
            @Override
            public void call(Subscriber<? super Recipe> subscriber) {
                mFavSubcriber = subscriber;
            }
        }).subscribe(new Action1<Recipe>() {
            @Override
            public void call(Recipe recipe) {
                String recipeId = recipe.getCook_id();
                if (recipe.isFav()) {
                    dislike(recipeId);
                } else {
                    like(recipeId);
                }
            }
        });
    }

    @Override
    public void like(String recipeId) {
        mLikeSubcriber.onNext(recipeId);
    }

    @Override
    public void dislike(String recipeId) {
        mDislikeSubcriber.onNext(recipeId);
    }

    @Override
    public void share(String recipeId) {

    }

    @Override
    public void addToShopList(String recipeId) {
        mAddShopSubcriber.onNext(recipeId);
    }

    @Override
    public void removeFromShopList(String recipeId) {
        mRemoveShopSubcriber.onNext(recipeId);
    }

    @Override
    public void checkAndChangeLikeStatus(Recipe recipe) {
        mFavSubcriber.onNext(recipe);
    }

    @Override
    public void checkAndChangeShopStatus(Recipe recipe) {
        mShopSubcriber.onNext(recipe);
    }
}
