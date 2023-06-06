package com.example.sqliteactivity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
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
        val deleteData = findViewById<Button>(R.id.deleteData)
        val queryData = findViewById<Button>(R.id.queryData)
        val updateData = findViewById<Button>(R.id.updateData)
        createDatabase.setOnClickListener{
            dbHelper.writableDatabase
        }
        insertData.setOnClickListener{
            val db = dbHelper.writableDatabase
            val values1 = ContentValues().apply {
                put("complete",false)
                put("thing","Do Homework")
                put("time","18:00")
            }
            db.insert("TODO",null,values1)
        }
        updateData.setOnClickListener{
            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("complete",true)
            val cursor = db.query("TODO",null,null,null,null,null,null)
            db.update("TODO",values,"id = ?", arrayOf("5"))
        }
        deleteData.setOnClickListener{
            val db = dbHelper.writableDatabase
            db.delete("TODO","id > ?", arrayOf("7"))
        }
        queryData.setOnClickListener{
            val db = dbHelper.writableDatabase
            val cursor = db.query("TODO",null,null,null,null,null,null)
            if(cursor.moveToFirst()){
                do{
                    val id = cursor.getInt(cursor.getColumnIndex("id"))
                    val complete = cursor.getShort(cursor.getColumnIndex("complete"))
                    val thing = cursor.getString(cursor.getColumnIndex("thing"))
                    val time = cursor.getString(cursor.getColumnIndex("time"))
                    Log.d("MainActivity","TODO id is $id")
                    Log.d("MainActivity","TODO complete is $complete")
                    Log.d("MainActivity","TODO thing is $thing")
                    Log.d("MainActivity","TODO time is $time")
                }while(cursor.moveToNext())
            }
            cursor.close()
        }

        val layoutManager = LinearLayoutManager(this)
        val recyclerView: RecyclerView = findViewById(R.id.recycler1)
        recyclerView.layoutManager = layoutManager
        val adapter = TODOAdapter(todoList_notfinish)
        recyclerView.adapter = adapter

        val layoutManager2 = LinearLayoutManager(this)
        val recyclerView2: RecyclerView = findViewById(R.id.recycler2)
        recyclerView2.layoutManager = layoutManager2
        val adapter2 = TODOAdapter(todoList_finish)
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