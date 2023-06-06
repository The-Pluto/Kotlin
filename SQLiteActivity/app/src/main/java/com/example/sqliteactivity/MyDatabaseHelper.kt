package com.example.sqliteactivity

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(val context:Context,name:String,version:Int):
        SQLiteOpenHelper(context, name, null, version){
    private val createTODO = "create table TODO (" +
            "id integer primary key autoincrement," +
            "thing text," +
            "time text," +
            "complete bool)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createTODO)
        Toast.makeText(context,"Create succeeded",Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists Book")
        onCreate(db)
    }


}