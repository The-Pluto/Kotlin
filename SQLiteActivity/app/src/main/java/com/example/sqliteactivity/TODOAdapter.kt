package com.example.sqliteactivity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class TODOAdapter(val activity:AppCompatActivity,val todoList:List<TODO>):
    RecyclerView.Adapter<TODOAdapter.ViewHolder>() {

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val complete: CheckBox = view.findViewById(R.id.isFinish)
        val thing: TextView = view.findViewById(R.id.things)
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
            Log.d("MainActivity","$len")
            val dbHelper = MyDatabaseHelper(activity, "TODOList.db", 2)
            val db = dbHelper.writableDatabase
            val complete = 1-todo.complete
            val values = ContentValues()
            values.put("complete",(1-todo.complete))
            Log.d("MainActivity","传入Values的值为:${values.get("complete")}")
            db.update("TODO",values,"id=?", arrayOf(todo.id.toString()))

            activity.finish()
            val intent = Intent(activity,MainActivity::class.java)
            activity.startActivity(intent)
        }
        holder.time.setOnClickListener{
            Log.d("MainActivity","点击了按钮${todo.id}")
            val intent = Intent(activity,DeleteUpdateActivity::class.java)

            intent.putExtra("id",todo.id.toString())
            intent.putExtra("time",todo.time)
            intent.putExtra("thing",todo.thing)
            activity.startActivity(intent)
        }
    }

    override fun getItemCount() = todoList.size

}
