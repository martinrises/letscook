package com.lzf.letscook.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzf.letscook.R;
import com.lzf.letscook.entity.CookStep;
import com.lzf.letscook.ui.adapter.StepAdapter;

import java.util.ArrayList;

/**
 * Created by asus on 2016/6/5.
 */
public class StepsFragment extends BaseFragment {

    public static final String EXTRA_STEPS = "steps";
    public static final String EXTRA_STEPS_POSITION = "position";

    private ViewPager vp;
    private StepAdapter mAdapter;

    private ArrayList<CookStep> mSteps;
    private int mStartIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        mSteps = (ArrayList<CookStep>) arguments.get(EXTRA_STEPS);
        mStartIndex =  arguments.getInt(EXTRA_STEPS_POSITION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_steps, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vp = (ViewPager) view.findViewById(R.id.step_vp);
        mAdapter = new StepAdapter(((FragmentActivity)mContext).getSupportFragmentManager(), mSteps);
        vp.setAdapter(mAdapter);
        vp.setCurrentItem(mStartIndex);
    }
}
