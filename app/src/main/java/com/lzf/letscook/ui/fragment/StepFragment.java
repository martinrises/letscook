package com.lzf.letscook.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lzf.letscook.R;
import com.lzf.letscook.entity.CookStep;
import com.lzf.letscook.ui.view.IndexView;

/**
 * Created by asus on 2016/6/5.
 */
public class StepFragment extends BaseFragment {

    public static final String EXTRA_STEP = "extra_step";

    private IndexView indexIxv;
    private SimpleDraweeView stepSdv;
    private TextView stepTv;

    private CookStep mStep;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStep = (CookStep) getArguments().get(EXTRA_STEP);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        indexIxv = (IndexView) view.findViewById(R.id.index_ixv);
        stepSdv = (SimpleDraweeView) view.findViewById(R.id.step_sdv);
        stepTv = (TextView) view.findViewById(R.id.step_tv);

        indexIxv.setIndex(mStep.getPosition());
        stepSdv.setImageURI(Uri.parse(mStep.getImage()));
        stepTv.setText(mStep.getContent());
    }
}
