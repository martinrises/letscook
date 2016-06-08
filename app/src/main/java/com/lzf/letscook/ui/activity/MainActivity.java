package com.lzf.letscook.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.lzf.letscook.R;
import com.lzf.letscook.ui.adapter.MainAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager mPgaer;
    private MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        mPgaer = (ViewPager) findViewById(R.id.main_vp);
        mAdapter = new MainAdapter(getSupportFragmentManager());
        mPgaer.setAdapter(mAdapter);
    }
}
