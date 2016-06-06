package com.lzf.letscook.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.lzf.letscook.R;
import com.lzf.letscook.ui.fragment.StepsFragment;

/**
 * Created by asus on 2016/6/5.
 */
public class StepsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_step);

        Bundle extras = getIntent().getExtras();
        StepsFragment stepsFragment = new StepsFragment();
        stepsFragment.setArguments(extras);

        getSupportFragmentManager().beginTransaction().add(R.id.holder_step_fragment, stepsFragment).commit();
    }
}
