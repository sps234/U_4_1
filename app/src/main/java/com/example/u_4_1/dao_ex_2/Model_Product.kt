package com.example.u_4_1.dao_ex_2

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "Products" )
data class Model_Product (
    @PrimaryKey( autoGenerate = false )
    val title : String,
    val img : ByteArray
)