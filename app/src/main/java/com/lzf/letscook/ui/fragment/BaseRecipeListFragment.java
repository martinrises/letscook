package com.lzf.letscook.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.lzf.letscook.R;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.ui.adapter.RecipeAdapter;
import com.lzf.letscook.ui.mvp.contract.RecipeListPresenter;
import com.lzf.letscook.ui.mvp.contract.RecipeListView;
import com.lzf.letscook.ui.view.RecipeItemDivider;

import java.util.List;

/**
 * Created by liuzhaofeng on 16/5/14.
 */
public abstract class BaseRecipeListFragment extends BaseFragment implements RecipeListView {

    public static final String TAG = BaseRecipeListFragment.class.getSimpleName();

    private RecyclerView recipeList;
    private SwipeRefreshLayout mRefreshLayout;
    private RecipeAdapter mAdapter;
    private ProgressBar loadMorePb;

    protected RecipeListPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = onCreatePresenter();
    }

    protected abstract RecipeListPresenter onCreatePresenter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recipeList = (RecyclerView) view.findViewById(R.id.recipe_list);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recipeList.setLayoutManager(llm);
        mAdapter = new RecipeAdapter();
        recipeList.setAdapter(mAdapter);
        recipeList.addItemDecoration(new RecipeItemDivider());
        recipeList.addOnScrollListener(mPresenter);

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
}
