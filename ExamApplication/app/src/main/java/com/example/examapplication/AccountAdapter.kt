package com.example.examapplication

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.NonDisposableHandle.parent
import java.io.File

class AccountAdapter(val activity:AppCompatActivity,val accountList:List<Account>):
    RecyclerView.Adapter<AccountAdapter.ViewHolder>() {
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val type:TextView = view.findViewById(R.id.type)
        val typeimage:ImageView = view.findViewById(R.id.typeImage)
        val money:TextView = view.findViewById(R.id.money)
        val thing:TextView = view.findViewById(R.id.things)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.account_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val account = accountList[position]
        holder.typeimage.setImageURI(Uri.fromFile(File(activity.externalCacheDir,"output_image.jpg")))
        holder.type.text = account.type
        holder.thing.text = account.things
        holder.money.text = account.money.toString()

        holder.thing.setOnClickListener{
            Log.d("MainActivity","点击了按钮${account.id}")
            val intent = Intent(activity,UpdateDeleteActivity::class.java)

            activity.intent.putExtra("type",account.type)
            activity.intent.putExtra("money",account.money)
            activity.intent.putExtra("things",account.things)
            activity.intent.putExtra("imageId",account.imageId)
            activity.intent.putExtra("judge",account.judge)
            activity.startActivity(intent)
        }
    }

    override fun getItemCount() = accountList.size
}