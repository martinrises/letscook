package com.lzf.letscook.ui.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lzf.letscook.ui.fragment.RecipeListFragment;

/**
 * Created by asus on 2016/6/29.
 */
public class RecipesPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TYPES = {"减肥食谱", "美容食谱", "健康食谱"};

    private final RecipeListFragment[] mFragments =  new RecipeListFragment[3];

    public RecipesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(mFragments[position] == null){
            Bundle args = generateArgs(TYPES[position], "1");
            mFragments[position] = new RecipeListFragment();
            mFragments[position].setArguments(args);
        }
        return mFragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TYPES[position];
    }

    @NonNull
    private Bundle generateArgs(String type, String order) {
        Bundle args = new Bundle();
        args.putString(RecipeListFragment.ARG_TYPE, type);
        args.putString(RecipeListFragment.ARG_ORDER, order);
        return args;
    }

    @Override
    public int getCount() {
        return TYPES.length;
    }
}
