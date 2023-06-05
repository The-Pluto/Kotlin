package com.example.wechatactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatFragment2: Fragment(), View.OnClickListener {
    private val msgList = ArrayList<Msg>()

    private var root:View ?= null

    private var adapter:MsgAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(root == null){
            root = inflater.inflate(R.layout.chat_activity,container,false)
        }
        initRecyclerview()
        initMsg()
        return root
    }

    override fun onClick(v: View?) {
        val send: Button? = root?.findViewById(R.id.send)
        val inputText: EditText? = root?.findViewById(R.id.inputText)
        val recycler_chat: RecyclerView? = root?.findViewById(R.id.recycler_chat)
        when(v){
            send->{
                val content = inputText?.text.toString()
                if(content.isNotEmpty()){
                    val msg = Msg(content,Msg.TYPE_SENT)
                    msgList.add(msg)
                    adapter?.notifyItemInserted(msgList.size - 1)
                    recycler_chat?.scrollToPosition(msgList.size - 1)
                    inputText?.setText("")
                }
            }
        }
    }

    private fun initMsg(){
        msgList.clear()
        val msg1 = Msg("Hello Wolrd",Msg.TYPE_RECEIVED)
        msgList.add(msg1)
        val msg2 = Msg("Hello!!!!!!!!!!!!!!!!",Msg.TYPE_SENT)
        msgList.add(msg2)
        val msg3 = Msg("It's the chat interface",Msg.TYPE_RECEIVED)
        msgList.add(msg3)
    }

    private fun initRecyclerview(){
        val recyclerview = root?.findViewById<RecyclerView>(R.id.recycler_chat)
        val recyclerview_adapter = MsgAdapter(msgList)
        if (recyclerview != null) {
            recyclerview.adapter = recyclerview_adapter
        };
        recyclerview?.layoutManager =
            LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
    }



}