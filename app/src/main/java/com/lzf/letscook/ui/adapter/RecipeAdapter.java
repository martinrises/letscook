package com.lzf.letscook.ui.adapter;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzf.letscook.R;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.ui.activity.DetailActivity;
import com.lzf.letscook.ui.view.RecipeItemView;
import com.lzf.letscook.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhaofeng on 16/5/14.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private static final float SCALE_RECIPE_ITEM_VIEW = 1.8f;
    private static final float OFFSET_FACTOR = 0.3f;

    private final List<Recipe> mRecipes = new ArrayList<>();
    private LayoutInflater mInflator;
    private RecyclerView mRecyclerView;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
                super.onScrolled(recyclerView, dx, dy);
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        int cn = recyclerView.getChildCount();
                        for (int i = 0; i < cn; i++) {
                            RecipeItemView child = (RecipeItemView) recyclerView.getChildAt(i);
                            Point p = getTranslation(recyclerView, child);
                            child.setTranslation(p.x, p.y);
                        }
                    }
                });
            }
        });
    }

    private Point getTranslation(RecyclerView recyclerView, RecipeItemView child) {
        Point p = new Point();
        float dy = child.getBottom() - recyclerView.getTop();
        float standardDy = (recyclerView.getHeight() + child.getHeight()) / 2;
        p.y = (int) (child.getHeight() * (dy - standardDy) / standardDy * OFFSET_FACTOR);
        return p;
    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflator == null) {
            mInflator = LayoutInflater.from(parent.getContext());
        }
        return new RecipeHolder(mInflator.inflate(R.layout.item_recipe_list, null));
    }

    @Override
    public void onBindViewHolder(final RecipeHolder holder, int position) {
        final Recipe recipe = mRecipes.get(position);
        holder.mRiv.setRecipe(recipe);
        holder.mRiv.setScale(SCALE_RECIPE_ITEM_VIEW);
        Point p = getTranslation(mRecyclerView, holder.mRiv);
        holder.mRiv.setTranslation(p.x, p.y);
        holder.mRiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.mRiv.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_RECIPE, recipe);
                holder.mRiv.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mRecipes == null) {
            return 0;
        }
        return mRecipes.size();
    }

    public void setData(List<Recipe> recipes) {
        mRecipes.clear();
        if (!Utils.isCollectionEmpty(recipes)) {
            mRecipes.addAll(recipes);
        }
    }

    public List<Recipe> getRecipes() {
        return mRecipes;
    }

    static final class RecipeHolder extends RecyclerView.ViewHolder {

        RecipeItemView mRiv;

        public RecipeHolder(View itemView) {
            super(itemView);
            mRiv = (RecipeItemView) itemView;
        }
    }
}
