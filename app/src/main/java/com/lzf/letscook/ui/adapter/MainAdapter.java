package com.lzf.letscook.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lzf.letscook.ui.fragment.RecipeListFragment;

/**
 * Created by asus on 2016/6/8.
 */
public class MainAdapter extends FragmentPagerAdapter {

    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new RecipeListFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
