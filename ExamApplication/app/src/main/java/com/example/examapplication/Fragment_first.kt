package com.example.examapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.w3c.dom.Text
import kotlin.concurrent.thread


class Fragment_first(val activity: AppCompatActivity) : Fragment() {

    private val expandList = ArrayList<Account>()
    private val incomeList = ArrayList<Account>()
    private var incometotal:Float = 0F
    private var expandtotal:Float = 0F
    val adapter1 = AccountAdapter(activity,expandList)
    val adapter2 = AccountAdapter(activity,incomeList)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = activity as MainActivity
        val dbHelper = MyDatabaseHelper(activity,"AccList.db",2)
        val db = dbHelper.writableDatabase
        return inflater.inflate(R.layout.first_fragment,container,false)
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n", "NotifyDataSetChanged")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val swipeRefresh = activity.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        swipeRefresh.setColorSchemeColors(com.google.android.material.R.color.design_dark_default_color_primary)
        swipeRefresh.setOnRefreshListener {
            thread{
                Thread.sleep(200)
                activity.runOnUiThread{
                    initAccount()
                    adapter1.notifyDataSetChanged()
                    adapter2.notifyDataSetChanged()
                    swipeRefresh.isRefreshing = true
                }
            }
        }

        initAccount()
        val total = activity.findViewById<TextView>(R.id.total)
        total.text = "Total income is ${incometotal},expand income is ${expandtotal}"

        val layoutManager1 = LinearLayoutManager(activity)
        val recyclerView: RecyclerView = activity.findViewById(R.id.recycler_expend)
        recyclerView.layoutManager = layoutManager1
        recyclerView.adapter = adapter1

        val layoutManager2 = LinearLayoutManager(activity)
        val recyclerView2: RecyclerView = activity.findViewById(R.id.recycler_income)
        recyclerView2.layoutManager = layoutManager2
        recyclerView2.adapter = adapter2


    }

    @SuppressLint("Range")
    private fun initAccount(){
        expandList.clear()
        incomeList.clear()
        val dbHelper = MyDatabaseHelper(activity,"AccList.db",2)
        val db = dbHelper.writableDatabase
        val cursor = db.query("Account",null,null,null,null,null,null)
        if(cursor.moveToFirst()){
            do{
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val things = cursor.getString(cursor.getColumnIndex("things"))
                val money = cursor.getFloat(cursor.getColumnIndex("money"))
                val type = cursor.getString(cursor.getColumnIndex("type"))
                val imageId = cursor.getInt(cursor.getColumnIndex("imageId"))
                val judge = cursor.getInt(cursor.getColumnIndex("judge"))

                if(judge == 1){
                    incomeList.add(Account(id,things,money,type,imageId,judge))
                    incometotal += money
                }
                else if(judge == 2){
                    expandList.add(Account(id,things,money,type,imageId,judge))
                    expandtotal += money
                }
            }while(cursor.moveToNext())
        }
        cursor.close()
    }



}