package com.example.u_4_1.dao_ex_2

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
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
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

class ProductMainAct : AppCompatActivity() {

//    private  lateinit var title : TextView
    private  lateinit var img : ImageView
    private  lateinit var pick : Button
    private  lateinit var save : Button
    private  lateinit var show : Button
    private  lateinit var listView: ListView

//    private val productRepo : ProductRepo? = null
    private val IMAGE_PICK_REQUEST = 1

    private lateinit var database: DB_Product

    private var selectedBitmap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pick = findViewById(R.id.pickBt21 )
        pick.setOnClickListener{
            pickImage()
        }

        database = Room.databaseBuilder( applicationContext, DB_Product::class.java, "Product_DB_2").build()


        save = findViewById(R.id.saveBt21)
        img = findViewById(R.id.img21 )
        listView = findViewById( R.id.lv21 )
        save.setOnClickListener {
            insertData()
        }

        show = findViewById(R.id.showBt21 )
        show.setOnClickListener {
            getData()
        }


    }

    private fun pickImage( ) {
        val intent = Intent( Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI )
        startActivityForResult( intent, IMAGE_PICK_REQUEST )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( requestCode == IMAGE_PICK_REQUEST && resultCode == Activity.RESULT_OK && data?.data != null ) {
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

    private fun insertData() {
        val title = findViewById<EditText>(R.id.text21 ).text.toString()
//        val image = findViewById<EditText>(R.id.img21 )
        if ( title.isNotEmpty() && selectedBitmap != null ) {
            GlobalScope.launch {
                database.productDao().insert( Model_Product( title, bitMapToByteArray( selectedBitmap!! )  ) )
            }
            Toast.makeText(this, "User saved", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(this, "Enter name and select an image", Toast.LENGTH_LONG).show()
        }

    }

    private fun getData() {
        database.productDao().getAllProducts().observe(this ) {
            products ->
            val adptr = AdapterProduct( this, R.layout.custom_ex_2_0, products )
            listView.adapter = adptr
        }
    }

    private fun bitMapToByteArray( bitMap : Bitmap ) : ByteArray {
        val stream = ByteArrayOutputStream()
        val bitmap2 = resizeBitmap( bitMap, 100, 150 )
        bitmap2.compress( Bitmap.CompressFormat.JPEG, 20, stream )
        return stream.toByteArray()
    }

    private fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val scale = minOf(maxWidth.toFloat() / width, maxHeight.toFloat() / height)

        val scaledWidth = (width * scale).toInt()
        val scaledHeight = (height * scale).toInt()

        return Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true)
    }
}