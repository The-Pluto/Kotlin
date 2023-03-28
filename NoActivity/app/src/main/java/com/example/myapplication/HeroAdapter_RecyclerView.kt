package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HeroAdapter_RecyclerView(val heroList:List<Hero>):
        RecyclerView.Adapter<HeroAdapter_RecyclerView.ViewHolder>() {

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val heroImage:ImageView = view.findViewById(R.id.heroImage)
        val heroName:TextView = view.findViewById(R.id.heroName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hero_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hero = heroList[position]
        holder.heroImage.setImageResource(hero.imageId)
        holder.heroName.text = hero.name
    }

    override fun getItemCount() = heroList.size

}