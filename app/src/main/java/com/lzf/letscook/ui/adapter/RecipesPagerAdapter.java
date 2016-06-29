package com.lzf.letscook.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lzf.letscook.ui.fragment.RecipeListFragment;

/**
 * Created by asus on 2016/6/29.
 */
public class RecipesPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TYPES = {"减肥食谱", "1", "2"};

    private RecipeListFragment f1;
    private RecipeListFragment f2;
    private RecipeListFragment f3;

    public RecipesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return TYPES.length;
    }
}
