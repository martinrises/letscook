package com.lzf.letscook.ui.mvp.contract;

/**
 * Created by asus on 2016/5/27.
 */
public interface RecipeDetailView {

    // 收藏了
    void onLike(String recipeId);

    // 取消收藏了
    void onDislike(String recipeId);

    // 显示分享的view
    void onShare(String recipeId);

    // 分享完成
    void onShareComplete(String recipeId);

    // 已添加到购物单
    void onAddToShopList(String recipeId);

    // 从购物单中移除
    void onRemoveToShopList(String recipeId);
}
