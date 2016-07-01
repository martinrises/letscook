package com.lzf.letscook.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lzf.letscook.ui.fragment.FavRecipeListFragment;
import com.lzf.letscook.ui.fragment.MainRecipeListFragment;
import com.lzf.letscook.ui.fragment.ShopListFragment;

/**
 * Created by asus on 2016/6/8.
 */
public class MainAdapter extends FragmentPagerAdapter {

    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MainRecipeListFragment();
            case 1:
                return new FavRecipeListFragment();
            case 2:
                return new ShopListFragment();
        }
        return null;

    }

    @Override
    public int getCount() {
        return 3;
    }
}
