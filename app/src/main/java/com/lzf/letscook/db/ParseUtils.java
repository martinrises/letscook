package com.lzf.letscook.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.lzf.letscook.db.contract.MajorContract;
import com.lzf.letscook.db.contract.QueryOrderContract;
import com.lzf.letscook.db.contract.RecipeContract;
import com.lzf.letscook.db.contract.StepContract;
import com.lzf.letscook.db.contract.TagContract;
import com.lzf.letscook.entity.CookStep;
import com.lzf.letscook.entity.Material;
import com.lzf.letscook.entity.Recipe;

import java.util.ArrayList;

/**
 * Created by liuzhaofeng on 16/5/3.
 */
public class ParseUtils {

    public static Recipe parseRecipe(Cursor recipeC, Cursor tagC, Cursor stepC, Cursor majorC, Cursor minorC) {

        Recipe recipe = parseRecipeMain(recipeC);
        if(recipe == null){
            return null;
        }

        ArrayList<String> tags = parseTags(tagC);
        recipe.setTags(tags);

        ArrayList<CookStep> steps = parseSteps(stepC);
        recipe.setSteps(steps);

        ArrayList<Material> majors = parseMaterial(majorC);
        recipe.setMajor(majors);

        ArrayList<Material> minors = parseMaterial(minorC);
        recipe.setMinor(minors);

        return recipe;
    }

    private static ArrayList<Material> parseMaterial(Cursor c) {

        ArrayList<Material> materials = new ArrayList<>();
        while (c.moveToNext()) {
            String title = c.getString(c.getColumnIndex(MajorContract.TITLE));
            String note = c.getString(c.getColumnIndex(MajorContract.NOTE));
            String image = c.getString(c.getColumnIndex(MajorContract.IMAGE));

            Material material = new Material(title, note, image);
            materials.add(material);
        }

        return materials;
    }

    private static ArrayList<CookStep> parseSteps(Cursor stepC) {

        ArrayList<CookStep> steps = new ArrayList<>();
        while (stepC.moveToNext()) {
            String position = stepC.getString(stepC.getColumnIndex(StepContract.POSITION));
            String content = stepC.getString(stepC.getColumnIndex(StepContract.CONTENT));
            String thumb = stepC.getString(stepC.getColumnIndex(StepContract.THUMB));
            String image = stepC.getString(stepC.getColumnIndex(StepContract.IMAGE));

            CookStep step = new CookStep(position, content, thumb, image);
            steps.add(step);
        }

        return steps;
    }

    private static ArrayList<String> parseTags(Cursor tagC) {

        ArrayList<String> tags = new ArrayList<>();
        while (tagC.moveToNext()) {
            String tag = tagC.getString(tagC.getColumnIndex(TagContract.TEXT));
            tags.add(tag);
        }

        return tags;
    }


    @NonNull
    private static Recipe parseRecipeMain(Cursor recipeC) {

        if(!recipeC.moveToFirst()){
            return null;
        }

        String recipeID = recipeC.getString(recipeC.getColumnIndex(RecipeContract.COOK_ID));
        Recipe recipe = new Recipe(recipeID);

        String title = recipeC.getString(recipeC.getColumnIndex(RecipeContract.TITLE));
        String image = recipeC.getString(recipeC.getColumnIndex(RecipeContract.IMAGE));
        String thumb_path = recipeC.getString(recipeC.getColumnIndex(RecipeContract.THUMB_PATH));
        String photo_path = recipeC.getString(recipeC.getColumnIndex(RecipeContract.PHOTO_PATH));
        String author_id = recipeC.getString(recipeC.getColumnIndex(RecipeContract.AUTHOR_ID));
        String tips = recipeC.getString(recipeC.getColumnIndex(RecipeContract.TIPS));
        String cookstory = recipeC.getString(recipeC.getColumnIndex(RecipeContract.COOKSTORY));
        String cook_time = recipeC.getString(recipeC.getColumnIndex(RecipeContract.COOK_TIME));
        String cook_difficulty = recipeC.getString(recipeC.getColumnIndex(RecipeContract.COOK_DIFFICULTY));
        String clicks = recipeC.getString(recipeC.getColumnIndex(RecipeContract.CLICKS));
        String create_time = recipeC.getString(recipeC.getColumnIndex(RecipeContract.CREATE_TIME));
        int recommended = recipeC.getInt(recipeC.getColumnIndex(RecipeContract.RECOMMENDED));
        String act_des = recipeC.getString(recipeC.getColumnIndex(RecipeContract.ACT_DES));
        String v_u = recipeC.getString(recipeC.getColumnIndex(RecipeContract.V_U));
        String author = recipeC.getString(recipeC.getColumnIndex(RecipeContract.AUTHOR));
        String author_photo = recipeC.getString(recipeC.getColumnIndex(RecipeContract.AUTHOR_PHOTO));
        int author_verified = recipeC.getInt(recipeC.getColumnIndex(RecipeContract.AUTHOR_VERIFIED));
        int collect_status = recipeC.getInt(recipeC.getColumnIndex(RecipeContract.COLLECT_STATUS));
        int comments_count = recipeC.getInt(recipeC.getColumnIndex(RecipeContract.COMMENTS_COUNT));
        int favo_counts = recipeC.getInt(recipeC.getColumnIndex(RecipeContract.FAVO_COUNTS));
        int dish_count = recipeC.getInt(recipeC.getColumnIndex(RecipeContract.DISH_COUNT));

        recipe.setTitle(title);
        recipe.setImage(image);
        recipe.setThumb_path(thumb_path);
        recipe.setPhoto_path(photo_path);
        recipe.setAuthor_id(author_id);
        recipe.setTips(tips);
        recipe.setCookstory(cookstory);
        recipe.setCook_time(cook_time);
        recipe.setCook_difficulty(cook_difficulty);
        recipe.setClicks(clicks);
        recipe.setCreate_time(create_time);
        recipe.setRecommended(recommended);
        recipe.setAct_des(act_des);
        recipe.setV_u(v_u);
        recipe.setAuthor(author);
        recipe.setAuthor_photo(author_photo);
        recipe.setAuthor_verified(author_verified);
        recipe.setCollect_status(collect_status);
        recipe.setComments_count(comments_count);
        recipe.setFavo_counts(favo_counts);
        recipe.setDish_count(dish_count);
        return recipe;
    }

    public static ContentValues getRecipeValues(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        ContentValues cv = new ContentValues();

        cv.put(RecipeContract.COOK_ID, recipe.getCook_id());
        cv.put(RecipeContract.TITLE, recipe.getTitle());
        cv.put(RecipeContract.IMAGE, recipe.getImage());
        cv.put(RecipeContract.THUMB_PATH, recipe.getThumb_path());
        cv.put(RecipeContract.PHOTO_PATH, recipe.getPhoto_path());
        cv.put(RecipeContract.AUTHOR_ID, recipe.getAuthor_id());
        cv.put(RecipeContract.TIPS, recipe.getTips());
        cv.put(RecipeContract.COOKSTORY, recipe.getCookstory());
        cv.put(RecipeContract.COOK_TIME, recipe.getCook_time());
        cv.put(RecipeContract.COOK_DIFFICULTY, recipe.getCook_difficulty());
        cv.put(RecipeContract.CLICKS, recipe.getClicks());
        cv.put(RecipeContract.CREATE_TIME, recipe.getCreate_time());
        cv.put(RecipeContract.RECOMMENDED, recipe.getRecommended());
        cv.put(RecipeContract.ACT_DES, recipe.getAct_des());
        cv.put(RecipeContract.V_U, recipe.getV_u());
        cv.put(RecipeContract.AUTHOR, recipe.getAuthor());
        cv.put(RecipeContract.AUTHOR_PHOTO, recipe.getAuthor_photo());
        cv.put(RecipeContract.AUTHOR_VERIFIED, recipe.getAuthor_verified());
        cv.put(RecipeContract.COLLECT_STATUS, recipe.getCollect_status());
        cv.put(RecipeContract.COMMENTS_COUNT, recipe.getComments_count());
        cv.put(RecipeContract.FAVO_COUNTS, recipe.getFavo_counts());
        cv.put(RecipeContract.DISH_COUNT, recipe.getDish_count());

        return cv;
    }

    public static ContentValues getQueryOrderValues(String query, String order, int start, int size, String cook_id) {

        ContentValues cv = new ContentValues();
        cv.put(QueryOrderContract.QUERY_ORDER, query + "_" + order);
        cv.put(QueryOrderContract.START_SIZE, start + "_" + size);
        cv.put(QueryOrderContract.RECIPE_ID, cook_id);
        return cv;
    }

    public static ContentValues getTagValues(String cook_id, String tag) {

        ContentValues cv = new ContentValues();
        cv.put(TagContract.RECIPE_ID, cook_id);
        cv.put(TagContract.TEXT, tag);
        return cv;
    }

    public static ContentValues getStepValues(String cook_id, CookStep step) {

        ContentValues cv = new ContentValues();
        cv.put(StepContract.RECIPE_ID, cook_id);
        cv.put(StepContract.CONTENT, step.getContent());
        cv.put(StepContract.POSITION, step.getPosition());
        cv.put(StepContract.IMAGE, step.getImage());
        cv.put(StepContract.THUMB, step.getThumb());
        return cv;
    }

    public static ContentValues getMaterialValues(String cook_id, Material material) {
        ContentValues cv = new ContentValues();
        cv.put(MajorContract.RECIPE_ID, cook_id);
        cv.put(MajorContract.IMAGE, material.getImage());
        cv.put(MajorContract.NOTE, material.getNote());
        cv.put(MajorContract.TITLE, material.getTitle());
        return cv;
    }
}
