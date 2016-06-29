package com.lzf.letscook.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzf.letscook.R;
import com.viewpagerindicator.TitlePageIndicator;

/**
 * Created by asus on 2016/6/29.
 */
public class MainRecipeListFragment extends BaseFragment {

    private ViewPager mPager;
    private TitlePageIndicator mIndicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipes, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPager = (ViewPager) view.findViewById(R.id.pager_main);
        mIndicator = (TitlePageIndicator) view.findViewById(R.id.titles_main);

        mPager.setAdapter();

        mIndicator.setViewPager(mPager);
    }
}
