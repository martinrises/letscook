package com.lzf.letscook.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzf.letscook.R;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.ui.activity.DetailActivity;
import com.lzf.letscook.ui.view.RecipeItemView;

import java.util.List;

/**
 * Created by liuzhaofeng on 16/5/14.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private List<Recipe> mRecipes;
    private LayoutInflater mInflator;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        int cn = recyclerView.getChildCount();
                        for(int i = 0; i < cn; i++){
                            View child = recyclerView.getChildAt(i);
//                            child.fin
                        }
                    }
                });
            }
        });
    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mInflator == null){
            mInflator = LayoutInflater.from(parent.getContext());
        }
        return new RecipeHolder(mInflator.inflate(R.layout.item_recipe_list, null));
    }

    @Override
    public void onBindViewHolder(final RecipeHolder holder, int position) {
        final Recipe recipe = mRecipes.get(position);
        holder.mRiv.setRecipe(recipe);
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
        if(mRecipes == null){
            return 0;
        }
        return mRecipes.size();
    }

    public void setData(List<Recipe> recipes){
        mRecipes = recipes;
    }

    public List<Recipe> getRecipes(){
        return mRecipes;
    }

    static final class RecipeHolder extends RecyclerView.ViewHolder{

        RecipeItemView mRiv;

        public RecipeHolder(View itemView) {
            super(itemView);
            mRiv = (RecipeItemView) itemView;
        }
    }
}
