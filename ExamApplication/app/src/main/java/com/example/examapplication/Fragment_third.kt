package com.example.examapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import java.io.File

class Fragment_third(val activity: AppCompatActivity) : Fragment() {

    val TakePhoto = 1
    val fromAlbum = 2
    lateinit var imageUri:Uri
    lateinit var outputImage:File

    @SuppressLint("CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = activity as MainActivity

        return inflater.inflate(R.layout.third_fragment,container,false)
    }

    @SuppressLint("CutPasteId")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dbHelper = MyDatabaseHelper(activity,"AccList.db",2)
        val insert = activity.findViewById<Button>(R.id.insert)

        var judge: Int? = null

        var array_confine2:Array<String> = arrayOf("餐饮","日用","学习")
        var array_confine1:Array<String> = arrayOf("工资","奖金","理财")
        var adapter1:ArrayAdapter<String> = ArrayAdapter(activity.baseContext,R.layout.spinner_item,array_confine1)
        var adapter2:ArrayAdapter<String> = ArrayAdapter(activity.baseContext,R.layout.spinner_item,array_confine2)
        val select = activity.findViewById<Spinner>(R.id.spinner)

        val radio = activity.findViewById<RadioGroup>(R.id.income_expand).
        setOnCheckedChangeListener{ group,checkedId->
            when(checkedId){
                R.id.income->{
                    judge = 1
                    adapter1.setDropDownViewResource(R.layout.drop_item)
                    select.prompt = "选择类别"
                    select.adapter = adapter1
                    select.setSelection(0)
                }
                R.id.expand-> {
                    judge = 2
                    adapter2.setDropDownViewResource(R.layout.drop_item)
                    select.prompt = "选择类别"
                    select.adapter = adapter2
                    select.setSelection(0)
                }
            }
        }

        var type: String? = null

        select.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(judge == 1){
                    Log.d("insert","选择类别:${array_confine1[position]}")
                    type = array_confine1[position]
                }
                else if(judge == 2){
                    Log.d("insert","选择类别:${array_confine2[position]}")
                    type = array_confine2[position]
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        insert.setOnClickListener{
            val db = dbHelper.writableDatabase
//            val type = activity.findViewById<Spinner>(R.id.leimu).selectedItem.toString()
            val money = activity.findViewById<EditText>(R.id.jine_edit).text.toString().toFloat()
            val thing = activity.findViewById<EditText>(R.id.shijian_edit).text.toString()
            val image = activity.findViewById<ImageView>(R.id.imageView).id

            val values1 = ContentValues().apply {
                put("things",thing)
                put("money", money)
                put("type", type)
                put("imageId",image)
                put("judge",judge)
            }
            db.insert("Account",null,values1)
            activity.finish()
            val intent: Intent = activity.intent
            startActivity(intent)
        }

        val takePhoto = activity.findViewById<Button>(R.id.takePhotoBtn)
        takePhoto.setOnClickListener{
            outputImage = File(activity.externalCacheDir,"output_image.jpg")
            if(outputImage.exists()){
                outputImage.delete()
            }
            outputImage.createNewFile()
            imageUri = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                FileProvider.getUriForFile(activity,"com.example.examapplication.fileprovider"
                    ,outputImage)
            }else{
                Uri.fromFile(outputImage)
            }
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
            startActivityForResult(intent,TakePhoto)
        }

        val fromAlbumBtn = activity.findViewById<Button>(R.id.fromAlbumBtn)
        fromAlbumBtn.setOnClickListener{
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent,fromAlbum)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            TakePhoto->{
                if(resultCode == Activity.RESULT_OK){
                    val bitmap = BitmapFactory.decodeStream(activity.contentResolver.
                    openInputStream(imageUri))
                    val imageView = activity.findViewById<ImageView>(R.id.imageView)
                    imageView.setImageBitmap(rotateIfRequired(bitmap))
                }
            }
            fromAlbum->{
                if(resultCode == Activity.RESULT_OK && data != null){
                    data.data?.let{uri->
                        val bitmap = getBitmapFromUri(uri)
                        val imageView = activity.findViewById<ImageView>(R.id.imageView)
                        imageView.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

    private fun getBitmapFromUri(uri: Uri) = activity.contentResolver
        .openFileDescriptor(uri,"r")?.use {
            BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
        }

    private fun rotateIfRequired(bitmap: Bitmap): Bitmap {
        val exif = ExifInterface(outputImage.path)
        val orientation = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
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

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
    }

}