package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        val button:Button = findViewById(R.id.button)
        button.setOnClickListener{
            val intent:Intent = Intent(this,RecyclerViewActivity::class.java)
            val username:EditText = findViewById(R.id.editTextTextPersonName)
            val password:EditText = findViewById(R.id.editTextTextPassword)
            val str1:String = username.text.toString()
            val str2:String = password.text.toString()
            if(str1 == "slc" && str2 == "123456"){
                startActivity(intent)
            }
            else{
                println("str1:$str1  \n str2:$str2")
                Toast.makeText(this,"Error Username or Password",Toast.LENGTH_SHORT).show()
            }
        }
    }
}