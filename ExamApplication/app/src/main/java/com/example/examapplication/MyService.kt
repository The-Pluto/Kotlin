package com.example.examapplication

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.provider.MediaStore.Audio.Media
import android.util.Log
import androidx.annotation.RequiresApi
import kotlin.concurrent.thread

class MyService : Service() {

    private val mBinder = DownloadBinder()
    private val mediaPlayer = MediaPlayer()

    class DownloadBinder:Binder(){
        fun startDownload(){
            Log.d("MyService","startDownload executed")
        }
        fun getProgress():Int{
            Log.d("MyService","getProgress executed")
            return 0
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        initMediaPlayer()
        mediaPlayer.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thread{
            if(!mediaPlayer.isPlaying){
                mediaPlayer.start()
                mediaPlayer.isLooping
            }
            stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    override fun onDestroy() {
        mediaPlayer.stop()
        mediaPlayer.release()
        super.onDestroy()
    }

    fun pause(){
        mediaPlayer.pause()
    }

    fun play(){
        mediaPlayer.start()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initMediaPlayer(){
        val assetManager = assets
        val fd = assetManager.openFd("XvSong1.mp3")
        mediaPlayer.setDataSource(fd.fileDescriptor,fd.startOffset,fd.length)
        mediaPlayer.prepare()
    }
}