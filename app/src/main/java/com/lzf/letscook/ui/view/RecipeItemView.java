package com.lzf.letscook.ui.view;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzf.letscook.R;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.util.RecipeUtils;

/**
 * Created by liuzhaofeng on 16/7/17.
 */
public class RecipeItemView extends RelativeLayout {

    private MoveSimpleDraweeView imgIv;
    private TextView titleTv;
    private TextView descTv;

    public RecipeItemView(Context context) {
        this(context, null);
    }

    public RecipeItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecipeItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_merge_recipe_list, this);

        imgIv = (MoveSimpleDraweeView) findViewById(R.id.recipe_img_iv);
        titleTv = (TextView) findViewById(R.id.recipe_title_tv);
        descTv = (TextView) findViewById(R.id.recipe_desc_tv);
    }

    public void setRecipe(Recipe recipe) {
        titleTv.setText(recipe.getTitle());
        descTv.setText(RecipeUtils.major2String(recipe.getMajor()));
        imgIv.setImageURI(Uri.parse(recipe.getImage()));
    }

    public void refreshOffset(int dx, int dy) {
        imgIv.translate(dx, dy);
    }
}
