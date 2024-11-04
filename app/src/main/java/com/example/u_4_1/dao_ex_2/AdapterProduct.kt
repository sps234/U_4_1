package com.example.u_4_1.dao_ex_2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.u_4_1.R

class AdapterProduct (private val mctx : Context, private val resources: Int, private val products : List<Model_Product >  )
    : ArrayAdapter<Model_Product>( mctx, resources, products ){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from( mctx )
        val view : View = layoutInflater.inflate( resources, null )

        val title : TextView = view.findViewById(R.id.txt22 )
        val img : ImageView = view.findViewById( R.id.img22 )

        val i : Model_Product = products[position]
        title.text = i.title
        img.setImageBitmap( byteArrayToBitmap( i.img ) )

        return view
    }

    private fun byteArrayToBitmap( byteArray: ByteArray ) : Bitmap {
        return BitmapFactory.decodeByteArray( byteArray, 0, byteArray.size )
    }
}