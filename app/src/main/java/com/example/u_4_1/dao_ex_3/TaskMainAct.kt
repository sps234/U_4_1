package com.example.u_4_1.dao_ex_3

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.u_4_1.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class TaskMainAct : AppCompatActivity() {

    private lateinit var tt : EditText
    private lateinit var img : ImageButton
    private lateinit var add : Button
    private lateinit var show : Button
    private lateinit var listView: ListView

    private val imagePickRequest = 1
    private var selectedBitmap : Bitmap? = null

    private lateinit var database : TaskDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_task_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        img = findViewById(R.id.imgBt31 )
        img.setOnClickListener{
            pickImage()
        }

        add = findViewById( R.id.saveBt31 )
        tt = findViewById(R.id.edT31 )
        database = Room.databaseBuilder( applicationContext, TaskDB::class.java, "TasksDB2" ).build()
        add.setOnClickListener {
            saveData()
        }

        show = findViewById(R.id.showBt31)
        listView = findViewById(R.id.lv31 )
        show.setOnClickListener {
            getTasks()
        }

    }

    private fun pickImage() {
        val intent = Intent( Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI )
        startActivityForResult( intent, imagePickRequest )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( requestCode == imagePickRequest && resultCode == Activity.RESULT_OK && data!!.data != null ) {
            val imageUri : Uri? = data.data
            if ( imageUri != null ) {
                selectedBitmap = if ( Build.VERSION.SDK_INT < 28 ) {
                    MediaStore.Images.Media.getBitmap( this.contentResolver, imageUri )
                }
                else {
                    val source = ImageDecoder.createSource( this.contentResolver, imageUri )
                    ImageDecoder.decodeBitmap( source )
                }
                img.setImageBitmap( selectedBitmap )
            }
        }
    }

    private fun saveData() {
        val t = tt.text.toString()
        if ( t.isNotEmpty() && selectedBitmap != null ) {

            GlobalScope.launch {
                database.taskDao().insert( TaskModel( t, bitMapToByteArray(selectedBitmap!!) ))
            }
            Toast.makeText(this, "Task Added", Toast.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(this, "Enter title and insert an image", Toast.LENGTH_LONG).show()
        }
    }

    private fun bitMapToByteArray( bitmap: Bitmap ) : ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress( Bitmap.CompressFormat.JPEG, 25, stream )
        return stream.toByteArray()
    }


    private fun getTasks()  {
            database.taskDao().getAllTasks().observe( this@TaskMainAct ) {
                    tasks ->
                val adptr = TaskAdapter( this@TaskMainAct, R.layout.custom_ex_3_0, tasks, database )
                listView.adapter = adptr
            }
    }


}