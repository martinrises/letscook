package com.lzf.letscook.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzf.letscook.R;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.system.CookSystem;
import com.lzf.letscook.ui.RecipeAdapter;
import com.lzf.letscook.util.Logger;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by liuzhaofeng on 16/5/14.
 */
public class RecipeListFragment extends BaseFragment {

    public static final String TAG = RecipeListFragment.class.getSimpleName();

    private RecyclerView recipeList;
    private SwipeRefreshLayout refreshRecipeLayout;

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
        refreshRecipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);

        refreshRecipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Observable<List<Recipe>> recipesOb = CookSystem.getInstance().getRecipes("减肥食谱", "1", 0, 10);
                recipesOb.subscribe(new Action1<List<Recipe>>() {
                    @Override
                    public void call(List<Recipe> recipes) {
                        Logger.v(TAG, recipes == null ? "recipe is null ###" : recipes.toString());

                        RecipeAdapter adapter = new RecipeAdapter();
                        adapter.setData(recipes);
                        recipeList.setAdapter(adapter);

                        refreshRecipeLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
