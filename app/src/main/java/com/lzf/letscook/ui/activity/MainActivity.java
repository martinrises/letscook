package com.lzf.letscook.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.lzf.letscook.R;
import com.lzf.letscook.ui.adapter.MainAdapter;
import com.lzf.letscook.ui.fragment.QueryRecipeListFragment;
import com.lzf.letscook.util.Utils;

public class MainActivity extends BaseActivity {

    private static final int TEXT_COLOR_GRAY = 0xff808080;
    private static final int TEXT_COLOR_BLUE = 0xff03A9F4;

    private static final String QUERY_FRAGMENT_TAG = "query_fragment_tag";

    private ViewPager mPager;
    private MainAdapter mAdapter;

    private Toolbar mToolbar;
    private TextView list_tab_main;
    private TextView fav_tab_main;
    private TextView shop_tab_main;

    private final TextView[] mIndicators = new TextView[3];
    private Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        initHandler();

        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        setSupportActionBar(mToolbar);

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

                if (positionOffset <= 0 || positionOffset >= 1) {
                    return;
                }

                mIndicators[position].setTextColor(Utils.getTransitionColor(TEXT_COLOR_BLUE, TEXT_COLOR_GRAY, positionOffset));
                mIndicators[position + 1].setTextColor(Utils.getTransitionColor(TEXT_COLOR_GRAY, TEXT_COLOR_BLUE, positionOffset));
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

    private void initHandler() {
        mHandler  = new Handler();
    }

    private void initIndicators() {
        for (int i = 0; i != mIndicators.length; i++) {
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
        for (int i = 0; i != mIndicators.length; i++) {
            mIndicators[i].setTextColor(position == i ? TEXT_COLOR_BLUE : TEXT_COLOR_GRAY);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String keyword) {
                Fragment queryFragment = getSupportFragmentManager().findFragmentByTag(QUERY_FRAGMENT_TAG);
                if(queryFragment == null){
                    queryFragment = new QueryRecipeListFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.main_container, queryFragment, QUERY_FRAGMENT_TAG).addToBackStack(null).commit();
                }

                final QueryRecipeListFragment f = (QueryRecipeListFragment) queryFragment;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        f.onSearch(keyword);
                    }
                });

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
