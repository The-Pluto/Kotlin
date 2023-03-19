package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_layout)
        val button2: Button = findViewById(R.id.button2)
        button2.setOnClickListener{
            val intent: Intent = Intent(this,FirstActivity::class.java)
            startActivity(intent)
//            Toast.makeText(this,"You click Button1",Toast.LENGTH_SHORT).show()
        }
    }
}