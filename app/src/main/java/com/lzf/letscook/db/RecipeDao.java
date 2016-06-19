package com.lzf.letscook.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.lzf.letscook.db.contract.FavoriteContract;
import com.lzf.letscook.db.contract.MajorContract;
import com.lzf.letscook.db.contract.MinorContract;
import com.lzf.letscook.db.contract.QueryOrderContract;
import com.lzf.letscook.db.contract.RecipeContract;
import com.lzf.letscook.db.contract.ShopContract;
import com.lzf.letscook.db.contract.StepContract;
import com.lzf.letscook.db.contract.TagContract;
import com.lzf.letscook.entity.CookStep;
import com.lzf.letscook.entity.Material;
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

        Cursor recipeC = null;
        try {
            String selection = QueryOrderContract.QUERY_ORDER + " = ? and " + QueryOrderContract.START_SIZE + " = ?";
            String[] args = new String[]{query + "_" + order, start + "_" + size};
            recipeC = db.query(QueryOrderContract.TABLE_NAME, null, selection, args, null, null, null);

            while(recipeC.moveToNext()){

                String recipe_id = recipeC.getString(recipeC.getColumnIndex(QueryOrderContract.RECIPE_ID));
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


        return recipes;
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

    public void writeRecipes(String query, String order, int start, int size, List<Recipe> recipes) {

        if(Utils.isCollectionEmpty(recipes))
            return;

        checkDd();

        // 数据插入DB
        for (Recipe recipe : recipes){

            writeRecipe(query, order, start, size, recipe);
        }
    }

    private void writeRecipe(String query, String order, int start, int size, Recipe recipe) {

        ContentValues queryOrderValues = ParseUtils.getQueryOrderValues(query, order, start, size, recipe.getCook_id());
        String where = QueryOrderContract.QUERY_ORDER +" = ? and " + QueryOrderContract.START_SIZE + " = ? and " + QueryOrderContract.RECIPE_ID + " = ?";
        String[] args = {query + "_" + order, start + "_" + size, recipe.getCook_id()};
        int cnt = db.update(QueryOrderContract.TABLE_NAME, queryOrderValues, where, args);
        if (cnt <= 0) {
            db.insert(QueryOrderContract.TABLE_NAME, null, queryOrderValues);
        }

        ContentValues cv = ParseUtils.getRecipeValues(recipe);
        String recipeWhere = RecipeContract.COOK_ID + " = ?";
        String[] recipeArgs = {recipe.getCook_id()};
        int recipeCnt = db.update(RecipeContract.TABLE_NAME, cv, recipeWhere, recipeArgs);
        if (recipeCnt <= 0) {
            db.insert(RecipeContract.TABLE_NAME, null, cv);
        }

        List<String> tags = recipe.getTags();
        String cook_id = recipe.getCook_id();
        if (tags != null) {
            for(String tag : tags){

                ContentValues tagCv = ParseUtils.getTagValues(cook_id, tag);
                String tagWhere = TagContract.RECIPE_ID + "=? and " + TagContract.TEXT + "=?";
                String[] tagArgs = {recipe.getCook_id(), tag};
                int tagCnt = db.update(TagContract.TABLE_NAME, tagCv, tagWhere, tagArgs);
                if (tagCnt <= 0) {
                    db.insert(TagContract.TABLE_NAME, null, tagCv);
                }
            }
        }

        List<CookStep> steps = recipe.getSteps();
        if(steps != null){
            for (CookStep step : steps){
                ContentValues stepCv = ParseUtils.getStepValues(cook_id, step);
                String stepWhere = StepContract.RECIPE_ID + "= ? and " + StepContract.POSITION + "=?";
                String[] stepArgs = {recipe.getCook_id(), step.getPosition()};
                int stepCnt = db.update(StepContract.TABLE_NAME, stepCv, stepWhere, stepArgs);
                if (stepCnt <= 0) {
                    db.insert(StepContract.TABLE_NAME , null, stepCv);
                }
            }
        }

        writeMaterial(cook_id, recipe.getMajor(), MajorContract.TABLE_NAME);

        writeMaterial(cook_id, recipe.getMinor(), MinorContract.TABLE_NAME);
    }

    private void writeMaterial(String cook_id, List<Material> major, String tableName) {
        if(major != null){

            for(Material material : major){

                ContentValues materialCv = ParseUtils.getMaterialValues(cook_id, material);
                String where = MajorContract.RECIPE_ID + " = ? and " + MajorContract.TITLE  + "= ?";
                String[] args = {cook_id, material.getTitle()};
                int cnt = db.update(tableName, materialCv, where, args);
                if (cnt <= 0) {
                    db.insert(tableName, null, materialCv);
                }
            }
        }
    }

    public void addFavorite(String recipeId){

        checkDd();

        String where = FavoriteContract.RECIPE_ID + " = ?";
        String[] args = {recipeId};
        ContentValues values = new ContentValues();
        values.put(FavoriteContract.RECIPE_ID, recipeId);
        int update = db.update(FavoriteContract.TABLE_NAME, values, where, args);
        if (update <= 0) {
            db.insert(FavoriteContract.TABLE_NAME, null, values);
        }
    }

    public void removeFavorite(String recipeId){

        checkDd();

        String where = FavoriteContract.RECIPE_ID + "= ?";
        String[] args = {recipeId};
        db.delete(FavoriteContract.TABLE_NAME, where, args);
    }

    public ArrayList<Recipe> getFavoriteRecipes(int start, int size){
        checkDd();

        ArrayList<Recipe> favRecipes = new ArrayList<>();

        String[] colunms = {FavoriteContract.RECIPE_ID};
        Cursor c = null;
        try{
            c = db.query(FavoriteContract.TABLE_NAME, colunms, null, null, null, null, null, start + "," + size);

            while(c.moveToNext()){
                String recipeId = c.getString(0);
                Recipe recipe = getRecipe(recipeId);
                favRecipes.add(recipe);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            closeCursor(c);
        }
        return favRecipes;
    }

    public void removeShop(String recipeId){

        checkDd();

        String where = ShopContract.RECIPE_ID + "= ?";
        String[] args = {recipeId};
        db.delete(ShopContract.TABLE_NAME, where, args);
    }

    public void addShop(String recipeId){

        checkDd();

        ContentValues values = new ContentValues();
        values.put(ShopContract.RECIPE_ID, recipeId);
        String where = ShopContract.RECIPE_ID + "= ?";
        String[] args = {recipeId};
        int update = db.update(ShopContract.TABLE_NAME, values, where, args);
        if(update <= 0){
            db.insert(ShopContract.TABLE_NAME, null, values);
        }
    }

    public ArrayList<Recipe> getShopRecipes(){
        checkDd();

        ArrayList<Recipe> shopRecipes = new ArrayList<>();

        String[] colunms = {ShopContract.RECIPE_ID};
        Cursor c = null;
        try{
            c = db.query(ShopContract.TABLE_NAME, colunms, null, null, null, null, null);

            while(c.moveToNext()){
                String recipeId = c.getString(0);
                Recipe recipe = getRecipe(recipeId);
                shopRecipes.add(recipe);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            closeCursor(c);
        }
        return shopRecipes;
    }

    private boolean buyMaterial(String materialId, boolean isMajor, boolean isBuyed){
        checkDd();

        String table = isMajor ? MajorContract.TABLE_NAME : MinorContract.TABLE_NAME;
        ContentValues values = new ContentValues();
        values.put(MajorContract.IS_BUYED, isBuyed ? 1 : 0);
        String where = MajorContract._ID + " = ?";
        String[] args = {materialId};
        int update = db.update(table, values, where, args);
        return update > 0;
    }

    public boolean buyMaterial(String materialId, boolean isMajor){
        return buyMaterial(materialId, isMajor, true);
    }

    public boolean unBuyMaterial(String materialId, boolean isMajor){
        return buyMaterial(materialId, isMajor, false);
    }

    public Boolean isFavorite(String recipeId) {
        checkDd();
        Cursor c = null;
        try{
            String where = FavoriteContract.RECIPE_ID + "= ?";
            String[] args = {recipeId};
            c = db.query(FavoriteContract.TABLE_NAME, null, where, args, null, null, null);

            return c.moveToFirst();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            closeCursor(c);
        }
        return false;
    }
}
