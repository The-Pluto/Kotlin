package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewActivity : AppCompatActivity() {
    private val heroList = ArrayList<Hero>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_layout)
        initHeroes()
        val layoutManager = LinearLayoutManager(this)
        val recyclerView:RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        val adapter = HeroAdapter_RecyclerView(heroList)
        recyclerView.adapter = adapter
    }
    private fun initHeroes(){
        repeat(10){
            heroList.add(Hero("雪莉",R.drawable.hero_xueli))
            heroList.add(Hero("妮塔",R.drawable.hero_nita))
            heroList.add(Hero("公牛",R.drawable.hero_gongniu))
        }
    }
}