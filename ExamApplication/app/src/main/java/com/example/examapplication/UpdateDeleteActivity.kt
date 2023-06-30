package com.example.examapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class UpdateDeleteActivity:AppCompatActivity() {
    var array_confine1:Array<String> = arrayOf("餐饮","日用","学习")
    var array_confine2:Array<String> = arrayOf("工资","奖金","理财")

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delete_update_activity)
        val dbHelper = MyDatabaseHelper(this, "AccList.db", 2)
        val delete = findViewById<Button>(R.id.delete)
        val update = findViewById<Button>(R.id.update)

        val edittext1 = findViewById<EditText>(R.id.editMoney2).text
        val edittext2 = findViewById<EditText>(R.id.editThing2).text
        val typeselect = findViewById<RadioGroup>(R.id.income_expand2)
        val judgeselect = findViewById<Spinner>(R.id.spinner2)

        val money = intent.getStringExtra("money")
        val thing = intent.getStringExtra("things")
        val type = intent.getStringExtra("type")
        val imageId = intent.getStringExtra("imageId")
        val judge = intent.getStringExtra("judge")

        edittext1.replace(0,edittext1.length,"123")
        edittext2.replace(0,edittext2.length,"thing")
        if (type != null) {
            typeselect.check(type.toInt())
        }

        findViewById<EditText>((R.id.editMoney2)).text = edittext1
        findViewById<EditText>((R.id.editThing2)).text = edittext2

        delete.setOnClickListener {
            val db = dbHelper.writableDatabase
            val id = intent.getStringExtra("id")
            Log.d("DeleteUpdateActivity","获取到的id号:$id")
            db.delete("Account","id = ?", arrayOf(id))
            finish()
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        update.setOnClickListener {
            val db = dbHelper.writableDatabase
            val values= ContentValues()
            val thing = findViewById<EditText>(R.id.editThing2).text
            val money = findViewById<EditText>(R.id.editMoney2).text
            val id = intent.getStringExtra("id")

            values.put("thing",thing.toString())
            db.update("Account",values,"id=?", arrayOf(id))

            values.put("money",thing.toString())
            db.update("Account",values,"id=?", arrayOf(id))


            finish()
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }
}