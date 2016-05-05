package com.lzf.letscook.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.lzf.letscook.db.contract.MajorContract;
import com.lzf.letscook.db.contract.MinorContract;
import com.lzf.letscook.db.contract.RecipeContract;
import com.lzf.letscook.db.contract.StepContract;
import com.lzf.letscook.db.contract.TagContract;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhaofeng on 16/5/3.
 */
public class RecipeDao {

    private SQLiteDatabase db ;

    private static RecipeDao sInstance;

    private RecipeDao(){
        db = RecipeOpenHelper.getInstance().getWritableDatabase();
    }

    public static RecipeDao getInstance(){
        if(sInstance == null){
            sInstance = new RecipeDao();
        }
        return sInstance;
    }

    public List<Recipe> getRecipes(String query, String order, int start, int size){

        checkDd();

        if(TextUtils.isEmpty(query) || TextUtils.isEmpty(order)){
            return null;
        }

        List<Recipe> recipes = new  ArrayList<>();

        String tableName = query + "_" + order;
        db.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, recipe_id TEXT)");

        Cursor recipeC = null;
        try {
            recipeC = db.rawQuery("select * from table ? where limit limit ?, ?", new String[]{tableName, start + "", size + ""});

            while(recipeC.moveToNext()){

                String recipe_id = recipeC.getString(recipeC.getColumnIndex("recipe_id"));
                Recipe recipe = getRecipe(recipe_id);

                if(recipe != null){
                    recipes.add(recipe);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCursor(recipeC);
        }


        return null;
    }

    private Recipe getRecipe(String recipe_id) {

        if(TextUtils.isEmpty(recipe_id)){
            return null;
        }

        Cursor recipeC = null, tagC = null, stepC = null, majorC = null, minorC = null;
        try {
            String[] args = {recipe_id};

            recipeC = db.query(RecipeContract.TABLE_NAME, null, RecipeContract.COOK_ID + "=?", args, null, null, null);
            tagC = db.query(TagContract.TABLE_NAME, null, TagContract.RECIPE_ID + "=?", args, null, null, null);
            stepC = db.query(StepContract.TABLE_NAME, null, StepContract.RECIPE_ID + "=?", args, null, null, null);
            majorC = db.query(MajorContract.TABLE_NAME, null, MajorContract.RECIPE_ID + "=?", args, null, null, null);
            minorC = db.query(MinorContract.TABLE_NAME, null, MinorContract.RECIPE_ID + "=?", args, null, null, null);
            return ParseUtils.parseRecipe(recipeC, tagC, stepC, majorC, minorC);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            closeCursor(recipeC);
            closeCursor(tagC);
            closeCursor(stepC);
            closeCursor(majorC);
            closeCursor(minorC);
        }
        return null;
    }

    private void closeCursor(Cursor cursor) {
        if(cursor != null){
            cursor.close();
        }
    }

    private void checkDd(){
        if(db == null || !db.isOpen()){
            db = RecipeOpenHelper.getInstance().getWritableDatabase();
        }
    }

    public void writeRecipes(String query, String order, List<Recipe> recipes) {

        if(Utils.isCollectionEmpty(recipes))
            return;

        checkDd();

        // 数据插入DB
        for (Recipe recipe : recipes){
            ContentValues cv = ParseUtils.getRecipeValues(recipe);
            db.insert(RecipeContract.TABLE_NAME, null, cv);
        }
    }
}
