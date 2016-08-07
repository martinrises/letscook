package com.lzf.letscook.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.lzf.letscook.LetsCook;
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
    private FloatingActionsMenu mFloatingActionsMenu;
    private FloatingActionButton mLikeBtn;
    private FloatingActionButton mShopBtn;
    private View mDimBg;

    private ObjectAnimator mHideFloatingAnimator;
    private ObjectAnimator mShowFloatingAnimator;
    private static long ANIMATION_DURATION = 500;
    private float mFloatingActionMenuY;
    private boolean mFloatingIsShowing = true;

    private RecipeDetailPresenter mDetailPresenter;

    private Recipe mRecipe;

    private int mScaledTouchSlop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ViewConfiguration vc = ViewConfiguration.get(this);
        mScaledTouchSlop = vc.getScaledTouchSlop();

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
        String desc = mRecipe.getCookstory();
        if (TextUtils.isEmpty(desc)) {
            mDescTv.setVisibility(View.GONE);
        } else {
            mDescTv.setText(desc);
        }

        mTitleTv = (TextView) findViewById(R.id.tv_detail_title);
        mTitleTv.setText(mRecipe.getTitle());

        mPhotoRecipe = (SimpleDraweeView) findViewById(R.id.sdv_recipe);
        mPhotoRecipe.setImageURI(Uri.parse(mRecipe.getPhoto_path()));

        mDimBg = findViewById(R.id.bg_dim_detail);
        mDimBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFloatingActionsMenu.collapse();
            }
        });

        mFloatingActionsMenu = (FloatingActionsMenu) findViewById(R.id.fam_detail);
        mFloatingActionsMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                mDimBg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMenuCollapsed() {
                mDimBg.setVisibility(View.GONE);
            }
        });
        mFloatingActionsMenu.post(new Runnable() {
            @Override
            public void run() {
                mFloatingActionMenuY = mFloatingActionsMenu.getY();
            }
        });

        pzs.setOnScrollListener(new PullToZoomScrollView.OnScrollListener() {

            private int lastToggleT = Integer.MIN_VALUE;

            @Override
            public void onScroll(int l, int t, int oldl, int oldt) {
                if(Math.abs(t - lastToggleT) >= mScaledTouchSlop){
                    toggleFloatingActionMenuShow(t < lastToggleT);
                    lastToggleT = t;
                }

            }
        });


        mLikeBtn = (FloatingActionButton) findViewById(R.id.btn_recipe_like);
        mLikeBtn.setIcon(mRecipe.isFav() ? R.drawable.detail_card_favorited: R.drawable.detail_card_favorite);
        mLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailPresenter.checkAndChangeLikeStatus(mRecipe);
                mFloatingActionsMenu.collapse();
            }
        });

        mShopBtn = (FloatingActionButton) findViewById(R.id.btn_recipe_shop);
        mShopBtn.setIcon(mRecipe.isInShopList() ? R.drawable.detail_card_shopped: R.drawable.detail_card_shop);
        mShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailPresenter.checkAndChangeShopStatus(mRecipe);
                mFloatingActionsMenu.collapse();
            }
        });
    }

    protected void toggleFloatingActionMenuShow(boolean shouldShow) {

        if(shouldShow != isFloatingMenuShowing()){
            if(shouldShow) {
                showFloatingMenu();
            } else {
                hideFloatingMenu();
            }
        }

    }

    private void hideFloatingMenu() {
        if( mHideFloatingAnimator == null){
            mHideFloatingAnimator = ObjectAnimator.ofFloat(mFloatingActionsMenu, "y", mFloatingActionMenuY, LetsCook.heightPixels);
            mHideFloatingAnimator.setDuration(ANIMATION_DURATION)
                    .setInterpolator(new AccelerateDecelerateInterpolator());
            mHideFloatingAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mFloatingActionsMenu.setVisibility(View.GONE);
                }
            });
        }
        mHideFloatingAnimator.start();
        mFloatingIsShowing = false;
    }


    private void showFloatingMenu() {
        if( mShowFloatingAnimator == null){
            mShowFloatingAnimator = ObjectAnimator.ofFloat(mFloatingActionsMenu, "y", LetsCook.heightPixels, mFloatingActionMenuY);
            mShowFloatingAnimator.setDuration(ANIMATION_DURATION)
                    .setInterpolator(new AccelerateDecelerateInterpolator());
            mShowFloatingAnimator.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationStart(Animator animation) {
                    mFloatingActionsMenu.setVisibility(View.VISIBLE);
                }
            });
        }
        mShowFloatingAnimator.start();
        mFloatingIsShowing = true;
    }

    private boolean isFloatingMenuShowing() {
        return mFloatingIsShowing;
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
        mShopBtn.setIcon(R.drawable.detail_card_shopped);
        Toast.makeText(this, "已添加", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveToShopList() {
        mRecipe.setInShopList(false);
        mShopBtn.setIcon(R.drawable.detail_card_shop);
        Toast.makeText(this, "已删除", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (!mFloatingActionsMenu.isExpanded()) {
            super.onBackPressed();
        } else {
            mFloatingActionsMenu.collapse();
        }
    }
}
