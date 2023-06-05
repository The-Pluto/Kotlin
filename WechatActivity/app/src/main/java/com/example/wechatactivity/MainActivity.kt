package com.example.wechatactivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity:AppCompatActivity() {
    private val fragment1 = Fragment_first()
    private val fragment2 = Fragment_second()
    private val fragment3 = Fragment_third()
    private val fragment4 = Fragment_forth()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1: Button = findViewById(R.id.button_main)
        val button2: Button = findViewById(R.id.button2_main)
        val button3: Button = findViewById(R.id.button3_main)
        val button4: Button = findViewById(R.id.button4_main)

        button1.setOnClickListener{
            replaceFragment(fragment1)
        }
        button2.setOnClickListener{
            replaceFragment(fragment2)
        }
        button3.setOnClickListener{
            replaceFragment(fragment3)
        }
        button4.setOnClickListener{
            replaceFragment(fragment4)
        }
        replaceFragment(fragment1)
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.firstFrag,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}