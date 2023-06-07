package com.example.multimediaactivity

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.VideoView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import java.io.File

class MainActivity:AppCompatActivity() {

    val TakePhoto = 1
    val fromAlbum = 2
    lateinit var imageUri:Uri
    lateinit var outputImage:File
    private val mediaPlayer = MediaPlayer()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val videoView = findViewById<VideoView>(R.id.videoView)
        val uri = Uri.parse("android.resource://$packageName/${R.raw.raindropsvidevo}")
        videoView.setVideoURI(uri)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel("normal","Normal",NotificationManager.
                IMPORTANCE_DEFAULT)
            val channel2 = NotificationChannel("important","Important",NotificationManager.
                IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
            manager.createNotificationChannel(channel2)
        }
        val sendNotice = findViewById<Button>(R.id.sendNotice)
        sendNotice.setOnClickListener{
            val intent = Intent(this,NotificationActivity::class.java)
            val pi = PendingIntent.getActivity(this,0,intent,0)

            val notification = NotificationCompat.Builder(this,"important")
                .setContentTitle("This is content title")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(resources,
                    R.drawable.ic_launcher_background))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setStyle(NotificationCompat.BigTextStyle().bigText("jeoifvgdnvsdjoijesorijvgoiesjiei " +
                        "eaf joseifiosd jiofesjdoi esnf cuisjdiocf jsdiojcsoljoidsno lsjdfoisdj oikjsfoild f" +
                        "egvidzsuifjfiedsn fuiesjdisdjiojm sdoijio sf"))
                .build()
            manager.notify(1,notification)
        }

        val takePhoto = findViewById<Button>(R.id.takePhotoBtn)
        takePhoto.setOnClickListener{
            outputImage = File(externalCacheDir,"output_image.jpg")
            if(outputImage.exists()){
                outputImage.delete()
            }
            outputImage.createNewFile()
            imageUri = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                FileProvider.getUriForFile(this,"com.example.multimediaactivity.fileprovider"
                    ,outputImage)
            }else{
                Uri.fromFile(outputImage)
            }
//            imageUri = FileProvider.getUriForFile(this,"com.example.cameraalbumtest." +
//                    "fileprovider",outputImage)
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
            startActivityForResult(intent,TakePhoto)
        }

        val fromAlbumBtn = findViewById<Button>(R.id.fromAlbumBtn)
        fromAlbumBtn.setOnClickListener{
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent,fromAlbum)
        }

        initMediaPlayer()

        val playSong = findViewById<Button>(R.id.playSong)
        playSong.setOnClickListener{
            if(!mediaPlayer.isPlaying){
                mediaPlayer.start()
            }
        }

        val pauseSong = findViewById<Button>(R.id.pauseSong)
        pauseSong.setOnClickListener{
            if(mediaPlayer.isPlaying){
                mediaPlayer.pause()
            }
        }

        val stopSong = findViewById<Button>(R.id.stopSong)
        stopSong.setOnClickListener{
            if(mediaPlayer.isPlaying){
                mediaPlayer.reset()
                initMediaPlayer()
            }
        }

        val playVideo = findViewById<Button>(R.id.playVideo)
        playVideo.setOnClickListener{
            if(!videoView.isPlaying){
                videoView.start()
            }
        }

        val pauseVideo = findViewById<Button>(R.id.pauseVideo)
        pauseVideo.setOnClickListener{
            if(videoView.isPlaying){
                videoView.pause()
            }
        }

        val stopVideo = findViewById<Button>(R.id.stopVideo)
        stopVideo.setOnClickListener{
            if(videoView.isPlaying){
                videoView.resume()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            TakePhoto->{
                if(resultCode == Activity.RESULT_OK){
                    val bitmap = BitmapFactory.decodeStream(contentResolver.
                    openInputStream(imageUri))
                    val imageView = findViewById<ImageView>(R.id.imageView)
                    imageView.setImageBitmap(rotateIfRequired(bitmap))
                }
            }
            fromAlbum->{
                if(resultCode == Activity.RESULT_OK && data != null){
                    data.data?.let{uri->
                        val bitmap = getBitmapFromUri(uri)
                        val imageView = findViewById<ImageView>(R.id.imageView)
                        imageView.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

    private fun rotateIfRequired(bitmap:Bitmap):Bitmap{
        val exif = ExifInterface(outputImage.path)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL)
        return when(orientation){
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap,90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap,180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap,270)
            else -> bitmap
        }
    }

    private fun rotateBitmap(bitmap:Bitmap,degree:Int):Bitmap{
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedBitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.width,bitmap.height,
            matrix,true)
        bitmap.recycle()
        return rotatedBitmap
    }

    private fun getBitmapFromUri(uri: Uri) = contentResolver
        .openFileDescriptor(uri,"r")?.use {
            BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
        }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initMediaPlayer(){
        val assetManager = assets
        val fd = assetManager.openFd("XvSong1.mp3")
        mediaPlayer.setDataSource(fd.fileDescriptor,fd.startOffset,fd.length)
        mediaPlayer.prepare()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
        val videoView = findViewById<VideoView>(R.id.videoView)
        videoView.suspend()
    }
}