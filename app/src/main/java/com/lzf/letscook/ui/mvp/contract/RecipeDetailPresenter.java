package com.lzf.letscook.ui.mvp.contract;

import com.lzf.letscook.entity.Recipe;

/**
 * 详情页的presenter
 * Created by asus on 2016/5/27.
 */
public interface RecipeDetailPresenter {

    // 添加到收藏
    void like(String recipeId);

    // 取消收藏
    void dislike(String recipeId);

    // 分享
    void share(String recipeId);

    // 添加到购物单
    void addToShopList(String recipeId);

    // 从购物单中移除
    void removeFromShopList(String recipeId);

    // 收藏按钮被点击一下
    void checkAndChangeLikeStatus(Recipe recipe);
}
