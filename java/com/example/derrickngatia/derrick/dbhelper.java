package com.example.derrickngatia.derrick;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DERRICK NGATIA on 6/26/2017.
 */
public class dbhelper extends SQLiteOpenHelper {
    public static final String DB_NAME="transcation.db";
    public static final int Version=1;
    public static final String column_name="message";
    public dbhelper(Context context) {
        super(context, DB_NAME,null,Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL(checkbalance.CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     db.execSQL(checkbalance.DROP_TABLE);
        onCreate(db);
    }
    public void insertData(String result){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("message",result);
        db.insert("balance",null,values);

    }
}
