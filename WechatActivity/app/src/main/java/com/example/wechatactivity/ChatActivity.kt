package com.example.wechatactivity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatActivity:AppCompatActivity(), View.OnClickListener {
    private val msgList = ArrayList<Msg>()

    private var adapter:MsgAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_activity)
        initMsg()
        val layoutManager = LinearLayoutManager(this)
        val recycler_chat:RecyclerView = findViewById(R.id.recycler_chat)
        recycler_chat.layoutManager = layoutManager
        adapter = MsgAdapter(msgList)
        recycler_chat.adapter = adapter
        val send:Button = findViewById(R.id.send)
        send.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        val send:Button = findViewById(R.id.send)
        val inputText:EditText = findViewById(R.id.inputText)
        val recycler_chat:RecyclerView = findViewById(R.id.recycler_chat)
        when(v){
            send->{
                val content = inputText.text.toString()
                if(content.isNotEmpty()){
                    val msg = Msg(content,Msg.TYPE_SENT)
                    msgList.add(msg)
                    adapter?.notifyItemInserted(msgList.size - 1)
                    recycler_chat.scrollToPosition(msgList.size - 1)
                    inputText.setText("")
                }
            }
        }
    }

    private fun initMsg(){
        val msg1 = Msg("Hello Wolrd",Msg.TYPE_RECEIVED)
        msgList.add(msg1)
        val msg2 = Msg("Hello!",Msg.TYPE_SENT)
        msgList.add(msg2)
        val msg3 = Msg("It's the chat interface",Msg.TYPE_RECEIVED)
        msgList.add(msg3)
    }


}