package com.lzf.letscook.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lzf.letscook.R;
import com.lzf.letscook.ui.adapter.MainAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager mPager;
    private MainAdapter mAdapter;

    private TextView list_tab_main;
    private TextView fav_tab_main;
    private TextView shop_tab_main;

    private final View[] mIndicators = new View[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        list_tab_main = (TextView) findViewById(R.id.list_tab_main);
        mIndicators[0] = list_tab_main;
        fav_tab_main = (TextView) findViewById(R.id.fav_tab_main);
        mIndicators[1] = fav_tab_main;
        shop_tab_main = (TextView) findViewById(R.id.shop_tab_main);
        mIndicators[2] = shop_tab_main;

        mPager = (ViewPager) findViewById(R.id.main_vp);
        mAdapter = new MainAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setIndicatorSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mIndicators[0].setBackgroundColor(Color.GRAY); // 一开始将第一个选中

        initIndicators();

    }

    private void initIndicators() {
        for(int i = 0 ; i != mIndicators.length; i++){
            final int pos = i;
            mIndicators[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPager.setCurrentItem(pos);
                }
            });
        }
    }

    private void setIndicatorSelected(int position) {
        for(int i = 0 ; i != mIndicators.length; i++){
            mIndicators[i].setBackgroundColor(i == position ? Color.GRAY : Color.WHITE);
        }
    }
}
