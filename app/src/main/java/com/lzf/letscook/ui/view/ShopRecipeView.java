package com.lzf.letscook.ui.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzf.letscook.R;
import com.lzf.letscook.entity.Material;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.util.Utils;

import java.util.ArrayList;

/**
 * Created by liuzhaofeng on 16/6/11.
 */
public class ShopRecipeView extends CardView{

    private Recipe mRecipe;
    private LinearLayout mContainerLl;

    public ShopRecipeView(Context context) {
        this(context, null);
    }

    public ShopRecipeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShopRecipeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mContainerLl = new LinearLayout(getContext());
        mContainerLl.setOrientation(LinearLayout.VERTICAL);
//        addView(mContainerLl, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        addView(mContainerLl, new FrameLayout.LayoutParams(200, 200));

        setRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, getResources().getDisplayMetrics()));
    }

    public void setRecipe(Recipe recipe){
        this.mRecipe = recipe;

        removeAllViews();
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 19, getResources().getDisplayMetrics()));

        TextView nameTv = new TextView(getContext());
        nameTv.setText(mRecipe.getTitle());
        mContainerLl.addView(nameTv, lp);

        TextView majorTv = new TextView(getContext());
        majorTv.setText(R.string.major_shop);
        mContainerLl.addView(majorTv);

        ArrayList<Material> major = mRecipe.getMajor();
        for(Material m : major){
            ShopItemView shopItemView = new ShopItemView(getContext());
            shopItemView.setMaterial(m);
            mContainerLl.addView(shopItemView, lp);
        }

        ArrayList<Material> minor = mRecipe.getMinor();
        if(!Utils.isCollectionEmpty(minor)){
            TextView minorTv = new TextView(getContext());
            minorTv.setText(R.string.minor_shop);
            mContainerLl.addView(minorTv);

            for(Material m : minor){
                ShopItemView shopItemView = new ShopItemView(getContext());
                shopItemView.setMaterial(m);
                mContainerLl.addView(shopItemView, lp);
            }
        }

    }

}
