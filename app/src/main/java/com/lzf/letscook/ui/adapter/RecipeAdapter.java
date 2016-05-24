package com.lzf.letscook.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lzf.letscook.R;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.ui.activity.DetailActivity;
import com.lzf.letscook.util.RecipeUtils;

import java.util.List;

/**
 * Created by liuzhaofeng on 16/5/14.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private List<Recipe> mRecipes;
    private LayoutInflater mInflator;

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

        holder.titleTv.setText(recipe.getTitle());
        holder.descTv.setText(RecipeUtils.major2String(recipe.getMajor()));
        holder.imgIv.setImageURI(Uri.parse(recipe.getImage()));

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.root.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_RECIPE, recipe);
                holder.root.getContext().startActivity(intent);
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

        View root;
        SimpleDraweeView imgIv;
        TextView titleTv;
        TextView descTv;

        public RecipeHolder(View itemView) {
            super(itemView);

            root = itemView;
            imgIv = (SimpleDraweeView) itemView.findViewById(R.id.recipe_img_iv);
            titleTv = (TextView) itemView.findViewById(R.id.recipe_title_tv);
            descTv = (TextView) itemView.findViewById(R.id.recipe_desc_tv);
        }
    }
}
