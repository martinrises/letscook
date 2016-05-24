package com.lzf.letscook.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.lzf.letscook.R;
import com.lzf.letscook.entity.Material;
import com.lzf.letscook.util.Utils;

import java.util.List;

/**
 * Created by liuzhaofeng on 16/5/20.
 */
public class MaterialView extends GridLayout{

    private List<Material> mMaterials;
    private LayoutInflater mInflater;

    public MaterialView(Context context) {
        super(context);
        init();
    }

    public MaterialView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MaterialView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setMaterials(List<Material> materials){
        this.mMaterials = materials;

        refreshUi();
    }

    private void init() {
        mInflater = LayoutInflater.from(getContext());
    }

    /**
     * 设置主料后，刷新视图
     */
    private void refreshUi() {

        removeAllViews();

        if(!Utils.isCollectionEmpty(mMaterials)){

            for(Material m : mMaterials){

                View itemView = mInflater.inflate(R.layout.item_material, null);
                ((TextView) itemView.findViewById(R.id.tv_material_name)).setText(m.getTitle());
                ((TextView) itemView.findViewById(R.id.tv_material_num)).setText(m.getNote());

                this.addView(itemView);
            }
        }
    }


}
