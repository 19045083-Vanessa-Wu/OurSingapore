package sg.edu.rp.c346.id19045083.oursingapore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "FoodieParadise.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FOOD = "Food";
    private static final String COLUMN_ID = "_id";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSongTableSQL = "CREATE TABLE " + TABLE_FOOD +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, location TEXT, description TEXT, stars INTEGER)";
        db.execSQL(createSongTableSQL);
        Log.i("INFO", "Created Tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        onCreate(db);
    }

    public long insertFood (String Name, String Location, String Description, int Stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", Name);
        values.put("location", Location);
        values.put("description", Description);
        values.put("stars", Stars);
        long result = db.insert(TABLE_FOOD, null, values);
        db.close();
        Log.d("SQL Insert", "ID:" + result); //ID returned, shouldn't be -1
        if (result == -1) {
            Log.d("DBHelper", "Insert Failed");
        }
        return result;
    }

    public ArrayList<Food> getAllFoods() {
        ArrayList<Food> foods = new ArrayList<Food>();

        String selectQuery = "SELECT " + COLUMN_ID + ", name, location, description, stars " +
                "FROM " + TABLE_FOOD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String location = cursor.getString(2);
                String description = cursor.getString(3);
                int stars = cursor.getInt(4);
                Food food = new Food(id, name, location, description, stars);
                foods.add(food);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return foods;
    }

    public int updateFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", food.getName());
        values.put("location", food.getLocation());
        values.put("description", food.getDescription());
        values.put("stars", food.getStars());
        String condition = COLUMN_ID + "=?";
        String[] args = {String.valueOf(food.get_id())};
        int result = db.update(TABLE_FOOD, values, condition, args);
        if (result < 1) {
            Log.d("DBHelper", "Update Failed");
        }
        db.close();
        return result;
    }

    public int deleteFood(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "=?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_FOOD, condition, args);
        if (result < 1) {
            Log.d("DBHelper", "Delete Failed");
        }
        db.close();
        return result;
    }

    public ArrayList<Food> getAllFoodsWithStars(int stars) {
        ArrayList<Food> foods = new ArrayList<Food>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, "name, location, description, stars"};
        String condition = "stars == ?";
        String[] args = {String.valueOf(stars)};
        Cursor cursor = db.query(TABLE_FOOD, columns, condition, args, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String location = cursor.getString(2);
                String description = cursor.getString(3);
                String Stars = cursor.getString(4);
                Food food = new Food(id, name, location, description, Integer.parseInt(Stars));
                foods.add(food);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return foods;
    }

} //DBHelper class

