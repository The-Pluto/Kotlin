package com.example.sqliteactivity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity:AppCompatActivity() {
    private val todoList_finish = ArrayList<TODO>()
    private val todoList_notfinish = ArrayList<TODO>()
    @SuppressLint("Range", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTodo()
        val dbHelper = MyDatabaseHelper(this,"TODOList.db",2)
        val createDatabase = findViewById<Button>(R.id.createDatabase)
        val insertData = findViewById<Button>(R.id.insertData)
        val queryData = findViewById<Button>(R.id.queryData)
        createDatabase.setOnClickListener{
            dbHelper.writableDatabase
        }
        insertData.setOnClickListener{
            val intent: Intent = Intent(this,InsertDataActivity::class.java)
            startActivity(intent)
        }
        queryData.setOnClickListener{
            val intent: Intent = Intent(this,QueryActivity::class.java)
            startActivity(intent)
        }

        val layoutManager = LinearLayoutManager(this)
        val recyclerView: RecyclerView = findViewById(R.id.recycler1)
        recyclerView.layoutManager = layoutManager
        val adapter = TODOAdapter(this,todoList_notfinish)
        recyclerView.adapter = adapter

        val layoutManager2 = LinearLayoutManager(this)
        val recyclerView2: RecyclerView = findViewById(R.id.recycler2)
        recyclerView2.layoutManager = layoutManager2
        val adapter2 = TODOAdapter(this,todoList_finish)
        recyclerView2.adapter = adapter2

    }

    @SuppressLint("Range")
    private fun initTodo(){
        todoList_finish.clear()
        todoList_notfinish.clear()
        val dbHelper = MyDatabaseHelper(this,"TODOList.db",2)
        val db = dbHelper.writableDatabase
        val cursor = db.query("TODO",null,null,null,null,null,null)
        if(cursor.moveToFirst()){
            do{
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val complete = cursor.getShort(cursor.getColumnIndex("complete"))
                val thing = cursor.getString(cursor.getColumnIndex("thing"))
                val time = cursor.getString(cursor.getColumnIndex("time"))
                if(complete == 1.toShort()){
                    todoList_finish.add(TODO(id,complete,thing,time))
                }
                else{
                    todoList_notfinish.add(TODO(id,complete,thing,time))
                }
            }while(cursor.moveToNext())
        }
        cursor.close()
    }

}