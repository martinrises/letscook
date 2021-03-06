package com.lzf.letscook.ui.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by liuzhaofeng on 16/5/17.
 */
public class RecipeItemDivider extends RecyclerView.ItemDecoration {

    private static final int DIVIDER_HEIGHT = 1;

    private Paint mPaint;

    public RecipeItemDivider() {
        super();
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#dedede"));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.bottom = DIVIDER_HEIGHT;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int dividerLeft = parent.getPaddingLeft();
        int dividerRight = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int dividerTop = child.getBottom() + params.bottomMargin;
            int dividerBottom = dividerTop + DIVIDER_HEIGHT;

            c.drawLine(dividerLeft, dividerTop, dividerRight, dividerBottom, mPaint);
        }
    }
}
