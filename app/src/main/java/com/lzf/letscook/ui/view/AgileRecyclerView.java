package com.lzf.letscook.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.lzf.letscook.util.Logger;

/**
 * Created by liuzhaofeng on 16/6/20.
 */
public class AgileRecyclerView extends RecyclerView {

    private static final String TAG = "AgileRcyclerView.Tag";

    private ViewConfiguration mVc;
    private int mScaledTouchSlop;

    private int mScrollPointId;
    private int mInitX, mInitY;

    public AgileRecyclerView(Context context) {
        this(context, null);
    }

    public AgileRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AgileRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mVc = ViewConfiguration.get(getContext());
        mScaledTouchSlop = mVc.getScaledTouchSlop();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//
//        final int actionIndex = MotionEventCompat.getActionIndex(ev);
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                Logger.v(TAG, "### ACTION_DOWN");
//                mScrollPointId = MotionEventCompat.getPointerId(ev, 0);
//                mInitX = (int) (ev.getX() + 0.5f);
//                mInitY = (int) (ev.getY() + 0.5f);
//                super.onTouchEvent(ev);
//                return false;
//
//            case MotionEventCompat.ACTION_POINTER_DOWN:
//                Logger.v(TAG, "*** ACTION_POINTER_DOWN");
//                mScrollPointId = MotionEventCompat.getPointerId(ev, actionIndex);
//                mInitX = (int) (MotionEventCompat.getX(ev, actionIndex) + 0.5f);
//                mInitY = (int) (MotionEventCompat.getY(ev, actionIndex) + 0.5f);
//                super.onTouchEvent(ev);
//                return false;
//
//            case MotionEvent.ACTION_MOVE:
//                Logger.v(TAG, "ACTION_MOVE");
//                final int index = MotionEventCompat.findPointerIndex(ev, mScrollPointId);
//                final int x = (int) (MotionEventCompat.getX(ev, index) + 0.5f);
//                final int y = (int) (MotionEventCompat.getY(ev, index) + 0.5f);
//                int dx = mInitX - x;
//                int dy = mInitY - y;
//
//                if (Math.abs(dy) > Math.abs(dx) && Math.abs(dx) > mScaledTouchSlop) {
//                    this.stopScroll();
//                    return false;
//                }
//                break;
//        }
//
//        return super.onTouchEvent(ev);
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        final int actionIndex = MotionEventCompat.getActionIndex(ev);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mScrollPointId = MotionEventCompat.getPointerId(ev, 0);
                mInitX = (int) (ev.getX() + 0.5f);
                mInitY = (int) (ev.getY() + 0.5f);
                break;

            case MotionEventCompat.ACTION_POINTER_DOWN:
                mScrollPointId = MotionEventCompat.getPointerId(ev, actionIndex);
                mInitX = (int) (MotionEventCompat.getX(ev, actionIndex) + 0.5f);
                mInitY = (int) (MotionEventCompat.getY(ev, actionIndex) + 0.5f);
                break;

            case MotionEvent.ACTION_MOVE:
                final int index = MotionEventCompat.findPointerIndex(ev, mScrollPointId);
                final int x = (int) (MotionEventCompat.getX(ev, index) + 0.5f);
                final int y = (int) (MotionEventCompat.getY(ev, index) + 0.5f);
                int dx = mInitX - x;
                int dy = mInitY - y;

                if (Math.abs(dx) > Math.abs(dy) && Math.abs(dx) > mScaledTouchSlop) {
                    Logger.v(TAG, "stopped");
                    this.stopScroll();
                }
                break;
        }

        boolean b = super.dispatchTouchEvent(ev);

        Logger.v(TAG, ev.getAction() + " >>> " + b);
        return b;
    }

}
