package com.lzf.letscook.util;

import com.lzf.letscook.entity.Material;

import java.util.List;

/**
 * Created by liuzhaofeng on 16/5/14.
 */
public class RecipeUtils {

    public static String major2String(List<Material> materials){

        if(Utils.isCollectionEmpty(materials)){
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for(Material m : materials){
            sb.append(m.getTitle());
            sb.append(" ");
        }
        return sb.toString();
    }
}
