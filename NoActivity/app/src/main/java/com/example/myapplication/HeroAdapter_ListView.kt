package com.example.myapplication

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class HeroAdapter_ListView(activity:Activity, val resourceId:Int, data: List<Hero>):
    ArrayAdapter<Hero>(activity,resourceId,data){

    //这个方法在每个子项被滚动到屏幕内时会被调用
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(resourceId,parent,false)
        val heroImage:ImageView = view.findViewById(R.id.heroImage)
        val heroName:TextView = view.findViewById(R.id.heroName)
        val hero = getItem(position)
        if(hero != null){
            heroImage.setImageResource(hero.imageId)
            heroName.text = hero.name
        }
        return view
    }

}