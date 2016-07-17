package com.lzf.letscook.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzf.letscook.R;

/**
 * Created by liuzhaofeng on 16/7/16.
 */
public class EmptyTipsFragment extends BaseFragment {

    public static final String ARGS_MSG = "msg";
    public static final String ARGS_ICON = "icon";

    private TextView mTipTv;
    private ImageView mTipIv;

    private int mMsgId;
    private int mIconId;

    public static void showEmptyTips(int msgId, int iconId, FragmentManager fm, int containerId, String tag) {
        Fragment tipFragment = fm.findFragmentByTag(tag);

        if(tipFragment == null){
            tipFragment = new EmptyTipsFragment();
            Bundle args = new Bundle();
            args.putInt(EmptyTipsFragment.ARGS_MSG, msgId);
            args.putInt(EmptyTipsFragment.ARGS_ICON, iconId);
            tipFragment.setArguments(args);
            fm.beginTransaction().add(containerId, tipFragment, tag).commit();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mMsgId = args.getInt(ARGS_MSG);
        mIconId = args.getInt(ARGS_ICON);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_empty_tip, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTipTv = (TextView) view.findViewById(R.id.tv_tip_empty);
        mTipIv = (ImageView) view.findViewById(R.id.iv_tip_empty);

        if(mMsgId != 0){
            mTipTv.setText(mMsgId);
        }

        if(mIconId != 0){
            mTipIv.setImageResource(mIconId);
        }else{
            mTipIv.setVisibility(View.GONE);
        }
    }
}
