package com.lzf.letscook.ui.activity;

import android.os.Bundle;

import com.lzf.letscook.R;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.ui.view.MaterialView;

/**
 * Created by liuzhaofeng on 16/5/20.
 */
public class DetailActivity extends BaseActivity{

    public static final String EXTRA_RECIPE = "RECIPE";
    MaterialView mMaterialView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Recipe recipe = (Recipe) getIntent().getSerializableExtra(EXTRA_RECIPE);
        mMaterialView = (MaterialView) findViewById(R.id.material_major);
        mMaterialView.setMaterials(recipe.getMajor());
    }
}
