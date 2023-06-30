package com.example.examapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.examapplication.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private val fragment1 = Fragment_first(this)
    private val fragment2 = Fragment_second()
    private val fragment3 = Fragment_third(this)
    private val fragment4 = Fragment_forth(this)

    private var activityMainBinding:ActivityMainBinding ?= null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem):Boolean{
        when(item.itemId){
            R.id.settings->{
                Toast.makeText(this,"You clicked Settings",
                    Toast.LENGTH_SHORT).show()
                showDefaultDialog()
            }
        }
        return true
    }

    @SuppressLint("MissingInflatedId", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val intent = Intent(this,MyService::class.java)
        startService(intent)

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

    fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.firstFrag,fragment)
        transaction.commit()
    }

    override fun onDestroy() {
        activityMainBinding = null
        super.onDestroy()
    }

    private fun showDefaultDialog(){
        val dialog= AlertDialog.Builder(this)
            .setIcon(R.drawable.ic_launcher_background)
            .setMessage("版本号:1.0\n制作人:邵梁铖\n简介:由西南大学邵梁铖制作的记账软件")
        dialog.apply {
            setTitle("帮助")
        }.create().show()
    }


}