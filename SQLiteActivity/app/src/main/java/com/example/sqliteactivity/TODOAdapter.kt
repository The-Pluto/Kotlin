package com.example.sqliteactivity

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TODOAdapter(val todoList:List<TODO>):
    RecyclerView.Adapter<TODOAdapter.ViewHolder>() {

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val complete: CheckBox = view.findViewById(R.id.isFinish)
        val thing: TextView = view.findViewById(R.id.thing)
        val time:TextView = view.findViewById(R.id.time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_item,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = todoList [position]
        holder.complete.isChecked = todo.complete == 1.toShort()
        holder.thing.text = todo.thing
        holder.time.text = todo.time
        holder.complete.setOnClickListener{
            val len = todoList.size
            todoList.drop(position)
            Log.d("MainActivity","$len")

        }
    }

    override fun getItemCount() = todoList.size

}
