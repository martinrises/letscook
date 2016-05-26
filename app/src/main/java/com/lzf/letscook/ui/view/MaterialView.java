package com.lzf.letscook.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.lzf.letscook.R;
import com.lzf.letscook.entity.Material;
import com.lzf.letscook.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhaofeng on 16/5/20.
 */
public class MaterialView extends GridLayout {

    private List<Material> mMaterials;
    private LayoutInflater mInflater;
    private Paint mPaint;
    private Path boderPath;

    public MaterialView(Context context) {
        super(context);
        init();
    }

    public MaterialView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MaterialView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setMaterials(List<Material> materials) {
        this.mMaterials = materials;

        refreshUi();
    }

    private void init() {
        mInflater = LayoutInflater.from(getContext());
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);

        boderPath = new Path();
    }

    /**
     * 设置主料后，刷新视图
     */
    private void refreshUi() {

        removeAllViews();

        if (!Utils.isCollectionEmpty(mMaterials)) {

            // 将mMaterials分成一对一对的
            List<List<Material>> pairs = pairMaterial(mMaterials);

            // 对mMaterials对 进行展示
            inflateMaterial(pairs);
        }
    }

    private void inflateMaterial(List<List<Material>> pairs) {
        for (List<Material> pair : pairs) {

            View itemView = mInflater.inflate(R.layout.item_material, null);

            Material m = pair.get(0);
            ((TextView) itemView.findViewById(R.id.tv_material_name)).setText(m.getTitle());
            ((TextView) itemView.findViewById(R.id.tv_material_num)).setText(m.getNote());

            if(pair.size() > 1){
                m = pair.get(0);
                ((TextView) itemView.findViewById(R.id.tv_material_name1)).setText(m.getTitle());
                ((TextView) itemView.findViewById(R.id.tv_material_num1)).setText(m.getNote());
            }

            this.addView(itemView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private List<List<Material>> pairMaterial(List<Material> materials) {
        List<List<Material>> pairs = new ArrayList<>();
        for (int i = 0; i < materials.size(); i = i + 2) {
            List<Material> pair = new ArrayList<>();
            pair.add(materials.get(i));

            if(i + 1 < materials.size()){
                pair.add(materials.get(i + 1));
            }

            pairs.add(pair);
        }
        return pairs;
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
