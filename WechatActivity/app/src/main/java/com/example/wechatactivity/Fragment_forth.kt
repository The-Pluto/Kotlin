package com.example.wechatactivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Fragment_forth: Fragment()  {
    private val PeopleList = ArrayList<People>()

    private var root:View ?= null

    val fragment = ChatFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceRightFragment(fragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(root == null){
            root = inflater.inflate(R.layout.first_fragment,container,false)
        }
        initRecyclerview()
        initPeople()
        return root
    }

    @SuppressLint("SimpleDateFormat")
    private fun initPeople(){
        val f1 = SimpleDateFormat("MM-dd HH:mm:ss")
        val s2 = f1.format(Date())
        PeopleList.clear()
        repeat(8){
            PeopleList.add(People("联系人1",R.drawable.hero_gongniu,"聊天内容", s2))
            PeopleList.add(People("联系人2",R.drawable.hero_nita,"聊天内容", s2))
            PeopleList.add(People("联系人3",R.drawable.hero_xueli,"聊天内容", s2))
        }
    }

    private fun initRecyclerview(){
        //获得Recyclerview
        val recyclerview = root?.findViewById<RecyclerView>(R.id.recycler_first)
        //创建adapter类的对象
        val recyclerview_adapter = RecyclerViewAdapter(PeopleList)
        //将对象作为参数通过setAdapter方法设置给recylerview；
        if (recyclerview != null) {
            recyclerview.adapter = recyclerview_adapter
        };
        //这步骤必须有，这是选择RecylerView的显示方式
        recyclerview?.layoutManager =
            LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
    }

    private fun replaceRightFragment(fragment: Fragment){
        val fragmentManager = getFragmentManager()
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.rightLayout,fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

}