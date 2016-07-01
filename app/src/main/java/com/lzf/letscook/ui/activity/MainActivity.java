package com.lzf.letscook.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.lzf.letscook.R;
import com.lzf.letscook.ui.adapter.MainAdapter;

public class MainActivity extends BaseActivity {

    private static final int TEXT_COLOR_GRAY = 0xff808080;
    private static final int TEXT_COLOR_BLUE = 0xff00a9d5;

    private ViewPager mPager;
    private MainAdapter mAdapter;

    private TextView list_tab_main;
    private TextView fav_tab_main;
    private TextView shop_tab_main;

    private final TextView[] mIndicators = new TextView[3];

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
        mPager.setOffscreenPageLimit(2);
        mAdapter = new MainAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if(positionOffset <= 0 || positionOffset >= 1){
                    return;
                }

                mIndicators[position].setTextColor(getTransitionColor(TEXT_COLOR_BLUE, TEXT_COLOR_GRAY, positionOffset));
                mIndicators[position + 1].setTextColor(getTransitionColor(TEXT_COLOR_GRAY, TEXT_COLOR_BLUE, positionOffset));
            }

            @Override
            public void onPageSelected(int position) {
                setIndicatorSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mIndicators[0].setTextColor(TEXT_COLOR_BLUE);

        initIndicators();

    }

    private int getTransitionColor(int src, int tag, float offset) {
        int r = Color.red(src) + (int) ((Color.red(tag) - Color.red(src)) * offset);
        int g = Color.green(src) + (int) ((Color.green(tag) - Color.green(src)) * offset);
        int b = Color.blue(src) + (int) ((Color.blue(tag) - Color.blue(src)) * offset);
        return Color.argb(0xff, r, g, b);
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
            mIndicators[i].setTextColor(position == i ? TEXT_COLOR_BLUE : TEXT_COLOR_GRAY);
        }
    }
}
