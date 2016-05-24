package com.lzf.letscook.net;

import android.text.TextUtils;

import com.lzf.letscook.entity.CookStep;
import com.lzf.letscook.entity.Material;
import com.lzf.letscook.entity.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhaofeng on 16/5/1.
 */
public class ParseUtils {

    /**
     * 将json解析成菜谱列表
     *
     * @param json
     * @return
     */
    public static List<Recipe> parseRecipes(String json) {

        if (TextUtils.isEmpty(json)) {
            return null;
        }

        try {
            JSONObject root = new JSONObject(json);
            String state = root.optString("state");

            if (!TextUtils.equals("success", state)) {
                return null;
            }

            List<Recipe> recipes = new ArrayList<>();
            JSONObject jResult = root.optJSONObject("result");
            JSONArray jRecipes = jResult.optJSONArray("recipes");
            for (int i = 0; i != jRecipes.length(); i++) {
                JSONObject jRecipe = jRecipes.optJSONObject(i);
                Recipe recipe = parseRecipe(jRecipe);

                if (recipe != null) {
                    recipes.add(recipe);
                }
            }

            return recipes;

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    private static Recipe parseRecipe(JSONObject jRecipe) {
        if (jRecipe == null) {
            return null;
        }

        String cook_id = jRecipe.optString("cook_id");
        Recipe recipe = new Recipe(cook_id);

        String title = jRecipe.optString("title");
        recipe.setTitle(title);

        String image = jRecipe.optString("image");
        recipe.setImage(image);

        String thumb_path = jRecipe.optString("thumb_path");
        recipe.setThumb_path(thumb_path);

        String photo_path = jRecipe.optString("photo_path");
        recipe.setPhoto_path(photo_path);

        ArrayList<String> tags = parseTags(jRecipe.optJSONArray("tags"));
        if (tags != null) {
            recipe.setTags(tags);
        }

        String author_id = jRecipe.optString("author_id");
        recipe.setAuthor_id(author_id);

        String tips = jRecipe.optString("tips");
        recipe.setTips(tips);

        String cookstory = jRecipe.optString("cookstory");
        recipe.setCookstory(cookstory);

        ArrayList<CookStep> cookSteps = parseCookSteps(jRecipe.optJSONArray("cookstep"));
        if (cookSteps != null) {
            recipe.setSteps(cookSteps);
        }

        String cook_time = jRecipe.optString("cook_time");
        recipe.setCook_time(cook_time);

        String cook_difficulty = jRecipe.optString("cook_difficulty");
        recipe.setCook_difficulty(cook_difficulty);

        String clicks = jRecipe.optString("clicks");
        recipe.setClicks(clicks);

        ArrayList<Material> major = parseMaterials(jRecipe.optJSONArray("major"));
        if (major != null) {
            recipe.setMajor(major);
        }

        ArrayList<Material> minor = parseMaterials(jRecipe.optJSONArray("minor"));
        if (minor != null) {
            recipe.setMinor(minor);
        }

        String create_time = jRecipe.optString("create_time");
        recipe.setCreate_time(create_time);

        int recommended = jRecipe.optInt("recommended");
        recipe.setRecommended(recommended);

        String act_des = jRecipe.optString("act_des");
        recipe.setAct_des(act_des);

        String v_u = jRecipe.optString("v_u");
        recipe.setV_u(v_u);

        String author = jRecipe.optString("author");
        recipe.setAuthor(author);

        String author_photo = jRecipe.optString("author_photo");
        recipe.setAuthor_photo(author_photo);

        int author_verified = jRecipe.optInt("author_verified");
        recipe.setAuthor_verified(author_verified);

        int collect_status = jRecipe.optInt("collect_status");
        recipe.setCollect_status(collect_status);

        int favo_counts = jRecipe.optInt("favo_counts");
        recipe.setFavo_counts(favo_counts);

        int comments_count = jRecipe.optInt("comments_count");
        recipe.setComments_count(comments_count);

        int dish_count = jRecipe.optInt("dish_count");
        recipe.setDish_count(dish_count);

        return recipe;
    }

    private static ArrayList<Material> parseMaterials(JSONArray jMaterials) {
        if (jMaterials == null) {
            return null;
        }

        ArrayList<Material> materials = new ArrayList<>();
        for (int i = 0; i != jMaterials.length(); i++) {
            JSONObject jMaterial = jMaterials.optJSONObject(i);
            Material material = parseMaterial(jMaterial);
            if (material != null) {
                materials.add(material);
            }
        }

        return materials;
    }

    private static Material parseMaterial(JSONObject jMaterial) {

        if (jMaterial == null) {
            return null;
        }

        String title = jMaterial.optString("title");
        String note = jMaterial.optString("note");
        String tu = jMaterial.optString("tu");

        Material material = new Material(title, note);
        material.setImage(tu);

        return material;
    }

    private static ArrayList<CookStep> parseCookSteps(JSONArray jCooksteps) {

        if (jCooksteps == null) {
            return null;
        }

        ArrayList<CookStep> steps = new ArrayList<>();
        for (int i = 0; i != jCooksteps.length(); i++) {
            JSONObject jStep = jCooksteps.optJSONObject(i);
            CookStep step = parseCookStep(jStep);
            if (step != null) {
                steps.add(step);
            }
        }

        return steps;
    }

    private static CookStep parseCookStep(JSONObject jStep) {
        if (jStep == null) {
            return null;
        }

        String position = jStep.optString("position");
        String content = jStep.optString("content");
        CookStep step = new CookStep(position, content);

        String thumb = jStep.optString("thumb");
        String image = jStep.optString("image");
        step.setContent(thumb);
        step.setImage(image);

        return step;

    }

    private static ArrayList<String> parseTags(JSONArray jTags) {

        if (jTags == null) {
            return null;
        }

        ArrayList<String> tags = new ArrayList<>();
        for (int i = 0; i != jTags.length(); i++) {
            JSONObject jTag = jTags.optJSONObject(i);
            String tag = jTag.optString("text");
            if (!TextUtils.isEmpty(tag)) {
                tags.add(tag);
            }
        }

        return tags;
    }
}
