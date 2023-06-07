package com.example.sqliteactivity

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class InsertDataActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insert_activity)
        val dbHelper = MyDatabaseHelper(this,"TODOList.db",2)
        val insert = findViewById<Button>(R.id.insert)

        insert.setOnClickListener{
            val db = dbHelper.writableDatabase
            val time = findViewById<EditText>(R.id.editTime).text.toString()
            val thing = findViewById<EditText>(R.id.editThing).text.toString()
            val values1 = ContentValues().apply {
                put("complete",false)
                put("thing", thing)
                put("time", time)
            }
            db.insert("TODO",null,values1)
            finish()
            val intent:Intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
}