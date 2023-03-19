package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_layout)
        val button1: Button = findViewById(R.id.button1)
        button1.setOnClickListener{
            val intent:Intent = Intent(this,SecondActivity::class.java)
            startActivity(intent)
//            Toast.makeText(this,"You click Button1",Toast.LENGTH_SHORT).show()
        }

    }

}