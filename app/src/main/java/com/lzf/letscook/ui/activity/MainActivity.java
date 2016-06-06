package com.lzf.letscook.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lzf.letscook.R;
import com.lzf.letscook.ui.fragment.RecipeListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        RecipeListFragment f = new RecipeListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.host, f).commit();
    }
}
