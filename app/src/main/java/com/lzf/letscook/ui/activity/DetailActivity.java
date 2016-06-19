package com.lzf.letscook.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.getbase.floatingactionbutton.FloatingActionButton;
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
    private SimpleDraweeView mPhotoRecipe;
    private FloatingActionButton mLikeBtn;

    private RecipeDetailPresenter mDetailPresenter;

    private Recipe mRecipe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDetailPresenter =  new RecipeDetailPresenterImpl(this);

        mRecipe = (Recipe) getIntent().getSerializableExtra(EXTRA_RECIPE);
        mMaterialView = (MaterialView) findViewById(R.id.material_major);
        mMaterialView.setMaterials(mRecipe.getMajor());

        mStepView = (StepView) findViewById(R.id.step_sv);
        mStepView.setSteps(mRecipe.getSteps());

        mDescTv = (TextView) findViewById(R.id.tv_detail_desc);
        mDescTv.setText(mRecipe.getTitle());

        mTitleTv = (TextView) findViewById(R.id.tv_detail_title);
        mTitleTv.setText(mRecipe.getCookstory());

        mPhotoRecipe = (SimpleDraweeView) findViewById(R.id.sdv_recipe);
        mPhotoRecipe.setImageURI(Uri.parse(mRecipe.getPhoto_path()));

        mLikeBtn = (FloatingActionButton) findViewById(R.id.btn_recipe_like);
        mLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailPresenter.checkAndChangeLikeStatus(mRecipe.getCook_id());
            }
        });
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
