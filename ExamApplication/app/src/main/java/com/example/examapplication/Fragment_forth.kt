package com.example.examapplication

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.examapplication.databinding.ActivityMainBinding

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
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initMediaPlayer(){
        val assetManager = activity.assets
        val fd = assetManager.openFd("XvSong1.mp3")
        mediaPlayer.setDataSource(fd.fileDescriptor,fd.startOffset,fd.length)
        mediaPlayer.prepare()
    }
}