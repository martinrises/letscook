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

    public void translate(int dx, int dy){
        mMatrix.postTranslate(dx, dy);
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
