package com.lzf.letscook.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by liuzhaofeng on 16/7/17.
 */
public class MoveSimpleDraweeView extends SimpleDraweeView {

    private final Matrix mMatrix = new Matrix();
    private float mScale = 0;
    private int mDx, mDy;
    private String flag = String.valueOf(Math.random()).substring(0, 6);

    public MoveSimpleDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public MoveSimpleDraweeView(Context context) {
        super(context);
    }

    public MoveSimpleDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoveSimpleDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MoveSimpleDraweeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setTranslation(int dx, int dy){
        mDx = dx;
        mDy = dy;

        mMatrix.reset();
        if(mScale != 0){
            mMatrix.postScale(mScale, mScale, getWidth() / 2, getHeight() / 2);
        }
        mMatrix.postTranslate(dx, dy);
        invalidate();
    }

    public void setScale(float scale){
        mScale = scale;

        mMatrix.reset();
        if(mDx != 0 || mDy != 0){
            mMatrix.postTranslate(mDx, mDy);
        }
        mMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int saveCount = canvas.save();
        canvas.concat(mMatrix);
        super.onDraw(canvas);
        canvas.restoreToCount(saveCount);
    }
}
