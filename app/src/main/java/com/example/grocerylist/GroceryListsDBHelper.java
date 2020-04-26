package com.example.grocerylist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.grocerylist.GroceryListsContract.*;

import androidx.annotation.Nullable;

 class GroceryListsDBHelper extends SQLiteOpenHelper {
     static final String DATABASE_NAME = "grocerylists.db";
    private static final int DATABASE_VERSION = 1;

    GroceryListsDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //check again
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_GROCERYLISTS_TABLE = "CREATE TABLE " +
                GroceryListEntry.TABLE_NAME + "( " +
                //    GroceryListEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GroceryListEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                GroceryListEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        db.execSQL(SQL_CREATE_GROCERYLISTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GroceryListEntry.TABLE_NAME);
    }
}
