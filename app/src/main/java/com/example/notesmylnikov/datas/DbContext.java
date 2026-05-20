package com.example.notesmylnikov.datas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbContext extends SQLiteOpenHelper {

    public static SQLiteDatabase sqLiteDatabase;

    public DbContext(Context context){
        super(context, "DbNotes", null, 1);

        sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Notes (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Title TEXT," +
                "Text TEXT," +
                "Date TEXT," +
                "Color TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
