package com.lzf.letscook.ui.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lzf.letscook.R;
import com.lzf.letscook.entity.CookStep;
import com.lzf.letscook.ui.activity.StepsActivity;
import com.lzf.letscook.ui.fragment.StepsFragment;
import com.lzf.letscook.util.Utils;

import java.util.ArrayList;

/**
 * Created by liuzhaofeng on 16/5/29.
 */
public class StepView extends LinearLayout {

    private ArrayList<CookStep> mSteps;
    private LayoutInflater mInflater;

    private Paint mPaint;
    private Path boderPath;

    public StepView(Context context) {
        this(context, null);
    }

    public StepView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);

        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.devide_line));
        mPaint.setStyle(Paint.Style.STROKE);

        boderPath = new Path();
    }

    public void setSteps(final ArrayList<CookStep> steps) {
        mSteps = steps;

        removeAllViews();

        if (Utils.isCollectionEmpty(mSteps)) {
            return;
        }

        for (final CookStep step : steps) {

            View itemView = mInflater.inflate(R.layout.item_step, null);
            ((TextView)itemView.findViewById(R.id.step_desc_tv)).setText(step.getPosition() + "." + step.getContent());

            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), StepsActivity.class);
                    intent.putExtra(StepsFragment.EXTRA_STEPS, steps);
                    intent.putExtra(StepsFragment.EXTRA_STEPS_POSITION, (Utils.parseInt(step.getPosition()) - 1));
                    getContext().startActivity(intent);
                }
            });

            String thumbStr = step.getThumb();
            SimpleDraweeView stepIv = (SimpleDraweeView) itemView.findViewById(R.id.step_img_iv);
            if(TextUtils.isEmpty(step.getThumb())){
                stepIv.setVisibility(View.GONE);
            }else{
                stepIv.setImageURI(Uri.parse(thumbStr));
            }

            addView(itemView);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        // 画边框
        drawBolder(canvas);
    }

    private void drawBolder(Canvas canvas) {

        int w = getHeight() - 1;
        int h = getWidth() - 1;

        boderPath.reset();
        boderPath.moveTo(0, 0);
        boderPath.lineTo(h, 0);
        boderPath.lineTo(h, w);
        boderPath.lineTo(0, w);
        boderPath.lineTo(0, 0);

        canvas.drawPath(boderPath, mPaint);
    }

}
