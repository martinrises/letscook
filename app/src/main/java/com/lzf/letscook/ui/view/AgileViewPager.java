package com.lzf.letscook.ui.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by asus on 2016/6/27.
 */
public class AgileViewPager extends ViewPager {

    private int mScrollPointId;
    private float mInitX, mInitY;
    private int mScaledTouchSlop;

    public AgileViewPager(Context context) {
        this(context, null);
    }

    public AgileViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mScrollPointId = MotionEventCompat.getPointerId(ev, 0);

                mInitX = ev.getX();
                mInitY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:

                int actionIndex = MotionEventCompat.findPointerIndex(ev, mScrollPointId);
                float x = MotionEventCompat.getX(ev, actionIndex);
                float y = MotionEventCompat.getY(ev, actionIndex);
                float dx = x - mInitX;
                float dy = y - mInitY;

                if (Math.abs(dx) > Math.abs(dy) && Math.abs(dx) > mScaledTouchSlop) {
                    return onTouchEvent(ev);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}
