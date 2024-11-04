package com.example.u_4_1.dao_ex_1

import androidx.room.Database
import androidx.room.RoomDatabase

@Database( entities = [Model_User::class], version = 1 )
abstract class User_DB : RoomDatabase() {
    abstract fun userDao() : UserDao
}