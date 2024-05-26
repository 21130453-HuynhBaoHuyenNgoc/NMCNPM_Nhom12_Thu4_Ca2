package com.example.myapplication.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "revenue_expenditure_management.db";
    private static final int DATABASE_VERSION = 1;
    // Tên bảng và các cột trong bảng createNote
    public static final String TABLE_CREATE_NOTE = "create_new_note";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DATE = "date";

    // Tên bảng và các cột trong bảng category


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng createNote
        String createNoteTableQuery = "CREATE TABLE " + TABLE_CREATE_NOTE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_AMOUNT + " REAL, " +
                COLUMN_TYPE + " TEXT," +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DATE + " TEXT);";
        db.execSQL(createNoteTableQuery);
        Log.d("DatabaseHelper", "onCreate() called");
        // Ta bảng category
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREATE_NOTE);
        onCreate(db);
    }
}
