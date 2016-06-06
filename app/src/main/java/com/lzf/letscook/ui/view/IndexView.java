package com.lzf.letscook.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by asus on 2016/6/5.
 */
public class IndexView extends View {

    private static final int PAINT_COLOR = Color.parseColor("#0080E3");

    private String mIndex;
    private int mTextHeight;
    private int mTextWidth;
    private float mStrokeWidth;

    private Paint mPaint;

    public IndexView(Context context) {
        this(context, null);
    }

    public IndexView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        mStrokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(PAINT_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()));
    }

    public void setIndex(String index) {
        mIndex = index;

        Rect bound = new Rect();
        mPaint.getTextBounds(mIndex, 0, 1, bound);
        mTextWidth = bound.width();
        mTextHeight = bound.height();

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        mPaint.setStrokeWidth(mStrokeWidth);
        canvas.drawCircle(width / 2, height / 2, (width - mStrokeWidth) / 2, mPaint);

        mPaint.setStrokeWidth(mStrokeWidth / 2);
        canvas.drawText(mIndex, (width - mTextWidth) / 2, (height + mTextHeight) / 2, mPaint);
    }
}
