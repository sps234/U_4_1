package com.example.u_4_1.dao_ex_3

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "Tasks" )
data class TaskModel(
    @PrimaryKey( autoGenerate = false )
    val title: String,
    val image : ByteArray
)
