package com.lzf.letscook.ui.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.lzf.letscook.LetsCook;
import com.lzf.letscook.R;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.system.fav.FavSystem;
import com.lzf.letscook.system.fav.OnFavChangeListener;
import com.lzf.letscook.system.shop.OnShopChangeListener;
import com.lzf.letscook.system.shop.ShopSystem;
import com.lzf.letscook.ui.adapter.RecipeAdapter;
import com.lzf.letscook.ui.mvp.contract.RecipeListPresenter;
import com.lzf.letscook.ui.mvp.contract.RecipeListView;
import com.lzf.letscook.ui.view.RecipeItemDivider;

import java.util.List;

/**
 * Created by liuzhaofeng on 16/5/14.
 */
public abstract class BaseRecipeListFragment extends BaseFragment implements RecipeListView, OnFavChangeListener , OnShopChangeListener{

    public static final String TAG = BaseRecipeListFragment.class.getSimpleName();


    private RecyclerView mRecipeList;
    private SwipeRefreshLayout mRefreshLayout;
    private RecipeAdapter mAdapter;
    private ProgressBar loadMorePb;

    protected RecipeListPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = onCreatePresenter();
        FavSystem.getInstance().addOnFavListener(this);
        ShopSystem.getInstance().addOnShopListener(this);
    }

    protected abstract RecipeListPresenter onCreatePresenter();

    protected int getLayoutId(){
        return -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        return inflater.inflate(layoutId == -1 ? R.layout.fragment_recipe_list : layoutId, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRecipeList = (RecyclerView) view.findViewById(R.id.recipe_list);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecipeList.setLayoutManager(llm);
        mAdapter = new RecipeAdapter();
        mRecipeList.setAdapter(mAdapter);
        mRecipeList.addItemDecoration(new RecipeItemDivider());
        mRecipeList.addOnScrollListener(mPresenter);
        Resources res = getResources();
        int maxRecycledViews = (LetsCook.heightPixels / res.getDimensionPixelSize(R.dimen.height_receip_item) + 1) * 2;
        mRecipeList.getRecycledViewPool().setMaxRecycledViews(0, maxRecycledViews);

        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);

        loadMorePb = (ProgressBar) view.findViewById(R.id.progress_load_more);

        mRefreshLayout.setOnRefreshListener(mPresenter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
                mPresenter.onRefresh();
            }
        });
    }

    @Override
    public void onSetRecipes(List<Recipe> recipes) {
        mAdapter.setData(recipes);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAppendRecipes(List<Recipe> recipes) {
        List<Recipe> cooks = mAdapter.getRecipes();
        if(cooks == null){
            cooks = recipes;
        }else{
            cooks.addAll(recipes);
        }
        mAdapter.setData(cooks);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void stopFresh() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void startLoad() {
        loadMorePb.post(new Runnable() {
            @Override
            public void run() {
                loadMorePb.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void stopLoad() {
        loadMorePb.post(new Runnable() {
            @Override
            public void run() {
                loadMorePb.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onFavChanged(String recipeId, boolean isFav) {
        List<Recipe> recipes = mAdapter.getRecipes();
        for (Recipe recipe : recipes) {
            if(TextUtils.equals(recipe.getCook_id(), recipeId)) {
                recipe.setIsFav(isFav);
                break;
            }
        }

        onSetRecipes(recipes);
    }

    @Override
    public void onShopChanged(String recipeId, boolean isShop) {
        List<Recipe> recipes = mAdapter.getRecipes();
        for (Recipe recipe : recipes) {
            if(TextUtils.equals(recipe.getCook_id(), recipeId)) {
                recipe.setIsFav(isShop);
                break;
            }
        }

        onSetRecipes(recipes);
    }
}
