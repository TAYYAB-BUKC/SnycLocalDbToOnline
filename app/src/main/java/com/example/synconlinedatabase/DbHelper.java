package com.example.synconlinedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.DropBoxManager;

public class DbHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    public final static String CREATE_TABLE_QUERY = "create table "+DbContract.DATABASE_NAME+"(Id integer primary key autoincrement,"+ DbContract.NAME+" text,"+DbContract.SYNC_STATUS+" integer);";
    public final static String DROP_TABLE_QUERY= "Drop Table if exists "+DbContract.TABLE_NAME;


    public DbHelper(Context context) {
        super(context, DbContract.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_QUERY);
        onCreate(db);
    }

    public void SaveToLocalDatabase(String name,int sync_status,SQLiteDatabase database)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.NAME,name);
        contentValues.put(DbContract.SYNC_STATUS,sync_status);
        database.insert(DbContract.TABLE_NAME,null,contentValues);
    }

    public Cursor readFromLocalDatabase(SQLiteDatabase database)
    {
        String[] projection = {DbContract.NAME,DbContract.SYNC_STATUS};

        return (database.query(DbContract.TABLE_NAME,projection,null,null,DbContract.NAME,null,null));

    }
}
