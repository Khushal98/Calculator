package com.example.dell.calculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHistory extends SQLiteOpenHelper {
    private static final String COL_1 = "ID";
    private static final String COL_2 = "EQUATION";
    private static final String COL_3 = "RESULT";
    private static final String DATABASE_NAME = "history.db";
    private static final String TABLE_NAME = "history_table";

    DatabaseHistory(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + COL_1 + " INTEGER PRIMARY KEY ," + COL_2 + " TEXT," + COL_3 + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String id, String equation, String result) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, equation);
        contentValues.put(COL_3, result);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result;
        result = db.rawQuery("select * from " + TABLE_NAME, null);
        return result;
    }

    public void updateData(String id, String equation, String result) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, equation);
        contentValues.put(COL_3, result);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
    }


    public void deleteAllData() {
        int index = getMaxIndex();
        for (int i = 1; i <= index; i++) {
            deleteData(Integer.toString(i));
        }
    }

    private void deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }

    public boolean shift() {
        Cursor res = getAllData();
        if (res.getCount() < 2)
            return false;
        res.moveToNext();
        int i = 1;
        while (res.moveToNext()) {
            updateData(Integer.toString(i), res.getString(1), res.getString(2));
            i++;
        }
        return true;
    }

    public int getMaxIndex() {
        Cursor res = getAllData();
        return res.getCount();
    }
}