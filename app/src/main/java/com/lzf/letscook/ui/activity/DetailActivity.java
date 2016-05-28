package com.lzf.letscook.ui.activity;

import android.os.Bundle;

import com.lzf.letscook.R;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.ui.mvp.contract.RecipeDetailPresenter;
import com.lzf.letscook.ui.mvp.contract.RecipeDetailView;
import com.lzf.letscook.ui.mvp.impl.RecipeDetailPresenterImpl;
import com.lzf.letscook.ui.view.MaterialView;

/**
 * Created by liuzhaofeng on 16/5/20.
 */
public class DetailActivity extends BaseActivity implements RecipeDetailView{

    public static final String EXTRA_RECIPE = "RECIPE";
    MaterialView mMaterialView;

    private RecipeDetailPresenter mDetailPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDetailPresenter =  new RecipeDetailPresenterImpl(this);

        setContentView(R.layout.activity_detail);
        Recipe recipe = (Recipe) getIntent().getSerializableExtra(EXTRA_RECIPE);
        mMaterialView = (MaterialView) findViewById(R.id.material_major);
        mMaterialView.setMaterials(recipe.getMajor());
    }

    @Override
    public void onLike(String recipeId) {

    }

    @Override
    public void onDislike(String recipeId) {

    }

    @Override
    public void onShare(String recipeId) {

    }

    @Override
    public void onShareComplete(String recipeId) {

    }

    @Override
    public void onAddToShopList(String recipeId) {

    }

    @Override
    public void onRemoveToShopList(String recipeId) {

    }
}
