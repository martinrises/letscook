package com.lzf.letscook.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lzf.letscook.entity.CookStep;
import com.lzf.letscook.ui.fragment.StepFragment;

import java.util.ArrayList;

/**
 * Created by asus on 2016/6/5.
 */
public class StepAdapter extends FragmentPagerAdapter {

    private ArrayList<CookStep> mSteps;

    public StepAdapter(FragmentManager fm, ArrayList<CookStep> steps) {
        super(fm);

        this.mSteps = steps;
    }

    @Override
    public Fragment getItem(int position) {
        CookStep step = mSteps.get(position);
        StepFragment f = new StepFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(StepFragment.EXTRA_STEP, step);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public int getCount() {
        return mSteps.size();
    }
}
