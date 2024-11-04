package com.example.u_4_1.dao_ex_1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import androidx.room.Room
import com.example.u_4_1.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class User_Ex_1 : AppCompatActivity() {

    private lateinit var database: User_DB
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_ex1)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        database = Room.databaseBuilder( applicationContext, User_DB::class.java , "DBUsers"  ).build()

        val addBtn = findViewById<Button>(R.id.addBtn)
        addBtn.setOnClickListener{

            val id = findViewById<EditText>(R.id.id1 ).text.toString().toInt()
            val tv1 = findViewById<EditText>(R.id.name1 ).text.toString()

            GlobalScope.launch {
                database.userDao().insert( Model_User( id, tv1 ) )
            }

            Toast.makeText( this, "Added user id $id and name $tv1 ", Toast.LENGTH_LONG ).show()

        }

        val showBtn = findViewById<Button>( R.id.showBtn )
        listView = findViewById(R.id.listView1 )
        showBtn.setOnClickListener{
            getData( )
        }

        listView.setOnItemLongClickListener { parent, view, position, id ->
            val view = parent.get( position )
            val id1 = view.findViewById<TextView>( R.id.textView3 ).text.toString().toInt()
            val name = view.findViewById<TextView>( R.id.textView4 ).text.toString()
            val builder = AlertDialog.Builder( this )
            builder.setTitle( "Edit User" )

            val layout = LinearLayout( this )
            layout.orientation = LinearLayout.VERTICAL

            val idView = EditText( this )
            idView.setText( id1.toString() )
            layout.addView( idView )

            val nameView = EditText( this )
            nameView.setText( name )
            layout.addView( nameView )

            builder.setView( layout )

            builder.setPositiveButton( "Edit" ) {
                dialog, which ->
                val updatedId = idView.text.toString().toInt()
                val updatedName = nameView.text.toString()

                GlobalScope.launch {
                    database.userDao().update( Model_User( updatedId, updatedName ) )
                }
            }

            builder.setNegativeButton( "Cancel" ){ dialog, which -> dialog.cancel() }
            builder.show()
            return@setOnItemLongClickListener true
        }


    }

    private fun getData(  ) {
        database.userDao().getUsers().observe( this ) { users ->
            val adptr = UserAdapter( this@User_Ex_1, R.layout.custom_ex_1, users )
            listView.adapter = adptr
        }
    }
}