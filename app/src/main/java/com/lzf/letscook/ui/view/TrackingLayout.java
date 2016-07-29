package com.lzf.letscook.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzf.letscook.R;
import com.lzf.letscook.util.Utils;

/**
 * Created by liuzhaofeng on 16/7/26.
 */
public class TrackingLayout extends LinearLayout implements PullToZoomScrollView.onHeadViewMovedOrScaledListener{

    private static final String ALPHA_TAG = "ALPHA_TAG";

    private TextView mFirstChild;
    private int mFirstChildHeight;

    private int mLastScrollBottom = Integer.MIN_VALUE;

    private static final int HEIGHT_THRESOLD = 100;
    private static int mInitBgColor, mInitTextColor, mEndTextColor;

    public TrackingLayout(Context context) {
        this(context, null);
    }

    public TrackingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrackingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        mFirstChild = (TextView) child;
        mFirstChild.post(new Runnable() {
            @Override
            public void run() {
                mFirstChildHeight = mFirstChild.getHeight();
            }
        });
        Resources res = getResources();
        mInitBgColor = res.getColor(R.color.bg_title_detail);
        mInitTextColor = res.getColor(R.color.text_start_title_detail);
        mEndTextColor = res.getColor(R.color.text_end_title_detail);
    }

    @Override
    public void onHeadViewMovedOrScaled(int bottom) {
        if(bottom == mLastScrollBottom){
            return;
        }

        mLastScrollBottom = bottom;

        int scrollY = -(Math.max(bottom, mFirstChildHeight) - mFirstChildHeight);

        setChildColor(scrollY);

        this.scrollTo(0, scrollY);
    }

    private float mLastScrollFactor = Float.MAX_VALUE;
    protected void setChildColor(int scrollY) {

        float scrollFactor;

        if(-scrollY <= HEIGHT_THRESOLD){
            // 根据scrollY改变mFirstChild的alpha值
            scrollFactor = ((float) (HEIGHT_THRESOLD - (-scrollY))) / HEIGHT_THRESOLD;
        } else if(-scrollY > HEIGHT_THRESOLD) {
            scrollFactor = 0;
        } else {
            scrollFactor = 1;
        }

        if(scrollFactor == mLastScrollFactor){
            return;
        }
        mLastScrollFactor = scrollFactor;

        int bgColor = Utils.getTransitionColor(mInitBgColor, Color.WHITE, scrollFactor);
        int textColor = Utils.getTransitionColor(mInitTextColor, mEndTextColor, scrollFactor);

        mFirstChild.setBackgroundColor(bgColor);
        mFirstChild.setTextColor(textColor);
    }
}
