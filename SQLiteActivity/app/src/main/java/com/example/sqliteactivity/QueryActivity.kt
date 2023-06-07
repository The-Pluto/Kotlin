package com.example.sqliteactivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class QueryActivity: AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "WrongViewCast", "Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.query_activity)

        var array_confine:Array<String> = arrayOf("大于","小于","等于")
        var adapter:ArrayAdapter<String> = ArrayAdapter(this,R.layout.spinner_item,array_confine)
        var TODOList: ArrayList<TODO>? = null

        val thing = findViewById<EditText>(R.id.thing)
        val confine = findViewById<Spinner>(R.id.confine)
        val time = findViewById<EditText>(R.id.time)
        val button = findViewById<Button>(R.id.query)

        adapter.setDropDownViewResource(R.layout.drop_item)
        confine.prompt = "选择限制条件"
        confine.adapter = adapter
        confine.setSelection(0)

        confine.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("QueryActivity","限制条件:${array_confine[position]}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        button.setOnClickListener{
            val dbHelper = MyDatabaseHelper(this,"TODOList.db",2)
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

            finish()
            val intent: Intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
}