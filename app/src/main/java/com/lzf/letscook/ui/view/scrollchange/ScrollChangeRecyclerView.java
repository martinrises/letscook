package com.lzf.letscook.ui.view.scrollchange;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by liuzhaofeng on 16/8/7.
 */
public class ScrollChangeRecyclerView extends RecyclerView {
    public ScrollChangeRecyclerView(Context context) {
        super(context);
    }

    public ScrollChangeRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollChangeRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);


    }
}
