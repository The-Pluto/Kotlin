package com.example.wechatactivity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(val peopleList:List<People>):

    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    inner class  ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val PeopleImage:ImageView = view.findViewById(R.id.peopleImage)
        val PeopleName:TextView = view.findViewById(R.id.peopleName)
        val PeopleContent:TextView = view.findViewById(R.id.peopleContent)
        val PeopleDate:TextView = view.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.people_item,parent,false)
        val viewHolder = ViewHolder(view)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val people = peopleList[position]
        holder.PeopleImage.setImageResource(people.imageId)
        holder.PeopleName.text = people.name
        holder.PeopleContent.text = people.content
        holder.PeopleDate.text = people.date.toString()
        holder.itemView.setOnClickListener{
            val position = holder.absoluteAdapterPosition
            val people = peopleList[position]
            Toast.makeText(holder.itemView.context,"you clicked image ${people.name}",
                Toast.LENGTH_SHORT).show()

        }
    }

    override fun getItemCount() = peopleList.size


}