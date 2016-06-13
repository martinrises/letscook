package com.lzf.letscook.ui.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by liuzhaofeng on 16/6/12.
 */
public class ProgressDialogFragment extends DialogFragment {

    private ProgressDialog pd;

    public ProgressDialogFragment(){
        super();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        pd = new ProgressDialog(getActivity());
        return pd;
    }

    public void showPd(FragmentManager fm, String msg){
        show(fm, "pd");
    }

    public void showPd(FragmentManager fm, int msgResId){
        show(fm, "pd");
    }
}
