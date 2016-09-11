package com.lzf.letscook.system.shop;

/**
 * Created by liuzhaofeng on 16/7/29.
 */
public interface OnShopChangeListener {

    void onShopChanged(String recipeId, boolean isShop);

    void onShopCleared();
}
