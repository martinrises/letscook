package com.lzf.letscook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lzf.letscook.LetsCook;
import com.lzf.letscook.db.contract.MajorContract;
import com.lzf.letscook.db.contract.MinorContract;
import com.lzf.letscook.db.contract.RecipeContract;
import com.lzf.letscook.db.contract.StepContract;
import com.lzf.letscook.db.contract.TagContract;

/**
 * Created by liuzhaofeng on 16/5/2.
 */
public class RecipeOpenHelper extends SQLiteOpenHelper {
    // recipe, recipe_tag, recipe_step, recipe_major, recipe_minor, user

    private static final String CREATE_RECIPE_TABLE = "CREATE TABLE IF NOT EXISTS " + RecipeContract.TABLE_NAME +
            "(" + RecipeContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            RecipeContract.COOK_ID + " TEXT ," +
            RecipeContract.TITLE + " TEXT ," +
            RecipeContract.IMAGE + " TEXT ," +
            RecipeContract.THUMB_PATH + " TEXT ," +
            RecipeContract.PHOTO_PATH + " TEXT ," +
            RecipeContract.AUTHOR_ID + " TEXT ," +
            RecipeContract.TIPS + " TEXT ," +
            RecipeContract.COOKSTORY + " TEXT ," +
            RecipeContract.COOK_TIME + " TEXT ," +
            RecipeContract.COOK_DIFFICULTY + " TEXT ," +
            RecipeContract.CLICKS + " TEXT ," +
            RecipeContract.CREATE_TIME + " TEXT ," +
            RecipeContract.RECOMMENDED + " TEXT ," +
            RecipeContract.ACT_DES + " TEXT ," +
            RecipeContract.V_U + " TEXT ," +
            RecipeContract.AUTHOR + " TEXT ," +
            RecipeContract.AUTHOR_PHOTO + " TEXT ," +
            RecipeContract.AUTHOR_VERIFIED + " TEXT ," +
            RecipeContract.COLLECT_STATUS + " TEXT ," +
            RecipeContract.COMMENTS_COUNT + " TEXT ," +
            RecipeContract.FAVO_COUNTS + " TEXT ," +
            RecipeContract.DISH_COUNT + " TEXT" +
            ")";

    private static final String CREATE_TAG_TABLE = "CREATE TABLE IF NOT EXISTS " + TagContract.TABLE_NAME +
            "(" + TagContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TagContract.TEXT + " TEXT, " +
            TagContract.RECIPE_ID + " TEXT" +
            ")";

    private static final String CREATE_STEP_TABLE = "CREATE TABLE IF NOT EXISTS " + StepContract.TABLE_NAME +
            "(" + StepContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            StepContract.POSITION + " TEXT ," +
            StepContract.CONTENT + " TEXT ," +
            StepContract.THUMB + " TEXT ," +
            StepContract.IMAGE + " TEXT, " +
            StepContract.RECIPE_ID + " TEXT" +
            ")";

    private static final String CREATE_MAJOR_TABLE = "CREATE TABLE IF NOT EXISTS " + MajorContract.TABLE_NAME +
            "(" + MajorContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MajorContract.TITLE + " TEXT ," +
            MajorContract.NOTE + " TEXT ," +
            MajorContract.IMAGE + " TEXT ," +
            MajorContract.RECIPE_ID + " TEXT" +
            ")";

    private static final String CREATE_MINOR_TABLE = "CREATE TABLE IF NOT EXISTS " + MinorContract.TABLE_NAME +
            "(" + MinorContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MinorContract.TITLE + " TEXT ," +
            MinorContract.NOTE + " TEXT ," +
            MinorContract.IMAGE + " TEXT, " +
            MajorContract.RECIPE_ID + " TEXT" +
            ")";


//    private static final String CREATE_MAJOR_TABLE = "CREATE TABLE IF NOT EXISTS " + ;

    private static RecipeOpenHelper sInstance;

    public static RecipeOpenHelper getInstance(){
        if (sInstance == null) {
            sInstance = new RecipeOpenHelper(LetsCook.getApp());
        }
        return sInstance;
    }

    private RecipeOpenHelper(Context context) {
        super(context, "recipe", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RECIPE_TABLE);
        db.execSQL(CREATE_TAG_TABLE);
        db.execSQL(CREATE_STEP_TABLE);
        db.execSQL(CREATE_MAJOR_TABLE);
        db.execSQL(CREATE_MINOR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO nothing
    }
}
