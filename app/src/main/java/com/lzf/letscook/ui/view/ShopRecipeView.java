package com.lzf.letscook.ui.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzf.letscook.R;
import com.lzf.letscook.entity.Material;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.system.shop.ShopSystem;
import com.lzf.letscook.ui.activity.DetailActivity;
import com.lzf.letscook.util.Utils;

import java.util.ArrayList;

/**
 * Created by liuzhaofeng on 16/6/11.
 */
public class ShopRecipeView extends CardView{

    private Recipe mRecipe;
    private LinearLayout mContainerLl;
    private LinearLayout mDelContainer;
    private ImageButton mDelBtn;

    private int mWidthHeightBtnDelete = 0;

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
        addView(mContainerLl, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        setRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, getResources().getDisplayMetrics()));

        mWidthHeightBtnDelete = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
    }

    public void setRecipe(Recipe recipe){
        this.mRecipe = recipe;

        mContainerLl.removeAllViews();
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView nameTv = new TextView(getContext());
        nameTv.setGravity(Gravity.CENTER);
        int peddingTopBottom = (int) getResources().getDimension(R.dimen.pedding_top_bottom_shop_title);
        nameTv.setPadding(0, peddingTopBottom, 0, peddingTopBottom);
        nameTv.setBackgroundColor(getResources().getColor(R.color.bg_title_shop));
        nameTv.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.arrow), null);
        nameTv.setMinLines(1);

        nameTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        nameTv.setText(mRecipe.getTitle());
        nameTv.setTextColor(getResources().getColorStateList(R.color.selector_name_shop));

        int paddingVerticalShop = getResources().getDimensionPixelSize(R.dimen.padding_vertival_name_shop);
        nameTv.setPadding(0, paddingVerticalShop, 0, paddingVerticalShop);
        mContainerLl.addView(nameTv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        nameTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_RECIPE, mRecipe);
                getContext().startActivity(intent);
            }
        });

        TextView majorTv = new TextView(getContext());
        majorTv.setText(R.string.major_shop);
        setMajorTvStyle(majorTv);
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
            setMajorTvStyle(minorTv);
            mContainerLl.addView(minorTv);

            for(Material m : minor){
                ShopItemView shopItemView = new ShopItemView(getContext());
                shopItemView.setMaterial(m);
                mContainerLl.addView(shopItemView, lp);
            }
        }

        // 删除的小按钮
        if(mDelBtn == null) {
            initDelBtn();
        }
        mContainerLl.addView(mDelContainer, lp);
    }

    protected void initDelBtn() {
        mDelContainer = new LinearLayout(getContext());
        mDelContainer.setOrientation(LinearLayout.HORIZONTAL);
        mDelContainer.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        mDelBtn = new ImageButton(getContext());
        mDelBtn.setBackgroundResource(android.R.color.transparent);
        mDelBtn.setLayoutParams(new ViewGroup.LayoutParams(mWidthHeightBtnDelete, mWidthHeightBtnDelete));
//        mDelBtn.setImageResource(R.drawable.action_delete_shop_selecotor);
        mDelBtn.setImageResource(R.drawable.btn_delete_shop_selector);
        mDelContainer.addView(mDelBtn, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mDelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopSystem.getInstance().removeShopRecipe(mRecipe.getCook_id()).subscribe();
            }
        });
    }

    private void setMajorTvStyle(TextView majorTv){
        majorTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        majorTv.setTextColor(getResources().getColor(R.color.text_shop_major_minor));
    }

}
