package com.example.examapplication

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.examapplication.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class Fragment_forth(val activity:AppCompatActivity) : Fragment() {

    private val mediaPlayer = MediaPlayer()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = activity as MainActivity
        return inflater.inflate(R.layout.forth_fragment,container,false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initMediaPlayer()

        val playSong = activity.findViewById<Button>(R.id.playSong)
        playSong.setOnClickListener{
            if(!mediaPlayer.isPlaying){
                mediaPlayer.start()
            }
        }

        val pauseSong = activity.findViewById<Button>(R.id.pauseSong)
        pauseSong.setOnClickListener{
            if(mediaPlayer.isPlaying){
                mediaPlayer.pause()
            }
        }

        val stopSong = activity.findViewById<Button>(R.id.stopSong)
        stopSong.setOnClickListener{
            if(mediaPlayer.isPlaying){
                mediaPlayer.reset()
                initMediaPlayer()
            }
        }

        val upload = activity.findViewById<Button>(R.id.upload)

        upload.setOnClickListener{
            var connection:HttpURLConnection ?= null
            try{
                val response = StringBuilder()
                val url = URL("https://baidu.com")
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                val input = connection.inputStream
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                    showResponse(response.toString())}
            }catch(e:Exception){
                e.printStackTrace()
            }finally {
                connection?.disconnect()
            }

        }
    }
    private fun showResponse(response:String){
        val responseText = activity.findViewById<TextView>(R.id.responseText)
        responseText.text = response
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initMediaPlayer(){
        val assetManager = activity.assets
        val fd = assetManager.openFd("XvSong1.mp3")
        mediaPlayer.setDataSource(fd.fileDescriptor,fd.startOffset,fd.length)
        mediaPlayer.prepare()
    }
}