package com.example.u_4_1.dao_ex_3

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.u_4_1.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskAdapter(private val mctx : Context, private val resources: Int, private val tasks : MutableList<TaskModel>, val database : TaskDB )
    : ArrayAdapter<TaskModel> ( mctx, resources, tasks ){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from( mctx )
        val view : View = layoutInflater.inflate( resources, null )

        val n = view.findViewById<TextView>(R.id.txt39 )
        val img = view.findViewById<ImageView>( R.id.imgBt38 )
        val del = view.findViewById<ImageButton>(R.id.imgBt39 )

        val i : TaskModel = tasks[position]
        n.text = i.title
        img.setImageBitmap( byteArrayToBitMap( i.image ) )

        del.setOnClickListener {
            tasks.removeAt( position )
            GlobalScope.launch {
                database.taskDao().delete( TaskModel( i.title, i.image ) )
            }
            notifyDataSetChanged()

        }

        return view
    }

    private fun byteArrayToBitMap( byteArray: ByteArray ) : Bitmap {
        return BitmapFactory.decodeByteArray( byteArray, 0, byteArray.size )
    }

}