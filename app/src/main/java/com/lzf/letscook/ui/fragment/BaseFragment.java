package com.lzf.letscook.ui.fragment;

import android.app.Fragment;
import android.content.Context;

/**
 * Created by liuzhaofeng on 16/5/14.
 */
public class BaseFragment extends Fragment{
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContext = null;
    }
}
