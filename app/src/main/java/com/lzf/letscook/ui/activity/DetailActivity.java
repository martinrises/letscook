package com.lzf.letscook.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.lzf.letscook.R;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.ui.mvp.contract.RecipeDetailPresenter;
import com.lzf.letscook.ui.mvp.contract.RecipeDetailView;
import com.lzf.letscook.ui.mvp.impl.RecipeDetailPresenterImpl;
import com.lzf.letscook.ui.view.MaterialView;
import com.lzf.letscook.ui.view.PullToZoomScrollView;
import com.lzf.letscook.ui.view.StepView;
import com.lzf.letscook.ui.view.TrackingLayout;

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
    private FloatingActionButton mShopBtn;

    private RecipeDetailPresenter mDetailPresenter;

    private Recipe mRecipe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDetailPresenter =  new RecipeDetailPresenterImpl(this);
        mRecipe = (Recipe) getIntent().getSerializableExtra(EXTRA_RECIPE);

        PullToZoomScrollView pzs = (PullToZoomScrollView) findViewById(R.id.pzs_detail);
        TrackingLayout tl = (TrackingLayout) findViewById(R.id.tl_detail);
        pzs.setOnHeadViewMovedOrScaledListener(tl);

        mMaterialView = (MaterialView) findViewById(R.id.material_major);
        mMaterialView.setMaterials(mRecipe.getMajor());

        mStepView = (StepView) findViewById(R.id.step_sv);
        mStepView.setSteps(mRecipe.getSteps());

        mDescTv = (TextView) findViewById(R.id.tv_detail_desc);
        mDescTv.setText(mRecipe.getCookstory());

        mTitleTv = (TextView) findViewById(R.id.tv_detail_title);
        mTitleTv.setText(mRecipe.getTitle());

        mPhotoRecipe = (SimpleDraweeView) findViewById(R.id.sdv_recipe);
        mPhotoRecipe.setImageURI(Uri.parse(mRecipe.getPhoto_path()));

        mLikeBtn = (FloatingActionButton) findViewById(R.id.btn_recipe_like);
        mLikeBtn.setIcon(mRecipe.isFav() ? R.drawable.detail_card_favorited: R.drawable.detail_card_favorite);
        mLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailPresenter.checkAndChangeLikeStatus(mRecipe);

            }
        });

        mShopBtn = (FloatingActionButton) findViewById(R.id.btn_recipe_shop);
        mShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailPresenter.checkAndChangeShopStatus(mRecipe);
            }
        });
    }

    @Override
    public void onLike() {
        mRecipe.setIsFav(true);
        mLikeBtn.setIcon(R.drawable.detail_card_favorited);
    }

    @Override
    public void onDislike() {
        mRecipe.setIsFav(false);
        mLikeBtn.setIcon(R.drawable.detail_card_favorite);
    }

    @Override
    public void onShare(String recipeId) {

    }

    @Override
    public void onShareComplete(String recipeId) {

    }

    @Override
    public void onAddToShopList() {
        mRecipe.setInShopList(true);
        Toast.makeText(this, "已添加", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveToShopList() {
        mRecipe.setInShopList(false);
        Toast.makeText(this, "已删除", Toast.LENGTH_SHORT).show();
    }
}
