package com.example.u_4_1.dao_ex_1

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "Users" )
data class Model_User(
    @PrimaryKey( autoGenerate = true )
    val id:Int,
    val name: String
)
