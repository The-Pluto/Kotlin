package com.example.sqliteactivity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class DeleteUpdateActivity:AppCompatActivity() {

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delete_update_activity)
        val dbHelper = MyDatabaseHelper(this, "TODOList.db", 2)
        val delete = findViewById<Button>(R.id.delete)
        val update = findViewById<Button>(R.id.update)

        val edittext1 = findViewById<EditText>(R.id.editTime2).text
        val edittext2 = findViewById<EditText>(R.id.editThing2).text

        val thing = intent.getStringExtra("thing")
        val time = intent.getStringExtra("time")

        edittext1.replace(0,edittext1.length,time)
        edittext2.replace(0,edittext2.length,thing)

        findViewById<EditText>((R.id.editTime2)).text = edittext1
        findViewById<EditText>((R.id.editThing2)).text = edittext2

        delete.setOnClickListener {
            val db = dbHelper.writableDatabase
            val id = intent.getStringExtra("id")
            Log.d("DeleteUpdateActivity","获取到的id号:$id")
            db.delete("TODO","id = ?", arrayOf(id))
            finish()
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        update.setOnClickListener {
            val db = dbHelper.writableDatabase
            val values=ContentValues()
            val thing = findViewById<EditText>(R.id.editThing2).text
            val time = findViewById<EditText>(R.id.editTime2).text
            val id = intent.getStringExtra("id")

            values.put("thing",thing.toString())
            db.update("TODO",values,"id=?", arrayOf(id))

            values.put("time",thing.toString())
            db.update("TODO",values,"id=?", arrayOf(id))
            finish()
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }

}