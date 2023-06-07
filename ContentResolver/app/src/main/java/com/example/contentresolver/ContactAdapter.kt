package com.example.contentresolver

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(val activity:AppCompatActivity, val contactList: ArrayList<Contact>):
    RecyclerView.Adapter<ContactAdapter.ViewHolder>(){

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val name: TextView = view.findViewById(R.id.name)
        val number:TextView = view.findViewById(R.id.number)
        val element: ConstraintLayout = view.findViewById(R.id.element)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactAdapter.ViewHolder, position: Int) {
        val contact = contactList[position]
        holder.name.text = contact.name
        holder.number.text = contact.number
        holder.element.setOnClickListener {
            Log.d("MainActivity","点击了联系人${contact.name}")
            if (ContextCompat.checkSelfPermission(
                    activity,
                    android.Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(android.Manifest.permission.CALL_PHONE), 1)
            } else {
                call(contact.number)
            }
        }
    }

    private fun call(number:String){
        try{
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$number")
            activity.startActivity(intent)
        }catch (e:SecurityException){
            e.printStackTrace()
        }
    }

    override fun getItemCount() = contactList.size


}