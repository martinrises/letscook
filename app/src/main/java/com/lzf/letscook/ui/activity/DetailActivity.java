package com.lzf.letscook.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.lzf.letscook.R;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.ui.mvp.contract.RecipeDetailPresenter;
import com.lzf.letscook.ui.mvp.contract.RecipeDetailView;
import com.lzf.letscook.ui.mvp.impl.RecipeDetailPresenterImpl;
import com.lzf.letscook.ui.view.MaterialView;
import com.lzf.letscook.ui.view.StepView;

/**
 * Created by liuzhaofeng on 16/5/20.
 */
public class DetailActivity extends BaseActivity implements RecipeDetailView{

    public static final String EXTRA_RECIPE = "RECIPE";
    private MaterialView mMaterialView;
    private StepView mStepView;
    private TextView mDescTv;
    private TextView mTitleTv;

    private RecipeDetailPresenter mDetailPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDetailPresenter =  new RecipeDetailPresenterImpl(this);

        setContentView(R.layout.activity_detail);
        Recipe recipe = (Recipe) getIntent().getSerializableExtra(EXTRA_RECIPE);
        mMaterialView = (MaterialView) findViewById(R.id.material_major);
        mMaterialView.setMaterials(recipe.getMajor());

        mStepView = (StepView) findViewById(R.id.step_sv);
        mStepView.setSteps(recipe.getSteps());

        mDescTv = (TextView) findViewById(R.id.tv_detail_desc);
        mDescTv.setText(recipe.getTitle());

        mTitleTv = (TextView) findViewById(R.id.tv_detail_title);
        mTitleTv.setText(recipe.getCookstory());

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
