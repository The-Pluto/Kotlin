package com.example.examapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(val  context: Context, name:String, version:Int):
        SQLiteOpenHelper(context,name,null,version){

    private val createAccount = "create table Account(" +
            "id integer primary key autoincrement," +
            "things text," +
            "money real," +
            "type text," +
            "imageId Int," +
            "judge Int)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createAccount)
        Toast.makeText(context,"Create succeeded", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists Account")
        onCreate(db)
    }

}