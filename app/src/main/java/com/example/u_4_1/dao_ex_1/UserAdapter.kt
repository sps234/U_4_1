package com.example.u_4_1.dao_ex_1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.u_4_1.R

class UserAdapter (private var mctx : Context, private var resources : Int, private var items : List<Model_User> )
    : ArrayAdapter<Model_User>( mctx, resources, items ) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from( mctx )
        val view : View = layoutInflater.inflate( resources, null )
        val id : TextView = view.findViewById(R.id.textView3 )
        val name : TextView = view.findViewById(R.id.textView4 )

        val mItem : Model_User = items[position]
        id.text = mItem.id.toString()
        name.text = mItem.name.toString()

        return view
    }

}