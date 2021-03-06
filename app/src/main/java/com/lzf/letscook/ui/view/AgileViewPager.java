package com.lzf.letscook.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import java.util.HashSet;

/**
 * Created by asus on 2016/6/27.
 */
public class AgileViewPager extends ViewPager {

    private float mInitX, mInitY;
    private int mScaledTouchSlop;

    private final HashSet<Integer> mNotInterceptIndexes = new HashSet<>();

    public AgileViewPager(Context context) {
        this(context, null);
    }

    public AgileViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOffscreenPageLimit(2);
        mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInitX = ev.getX();
                mInitY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:

                float x = ev.getX();
                float y = ev.getY();
                float dx = x - mInitX;
                float dy = y - mInitY;

                if (Math.abs(dx) > Math.abs(dy) && Math.abs(dx) > mScaledTouchSlop && shouldIntercept()) {
                    return onTouchEvent(ev);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setNotInterceptIndex(int index){
        this.mNotInterceptIndexes.add(index);
    }

    private boolean shouldIntercept() {
        return !mNotInterceptIndexes.contains(getCurrentItem());
    }

}
