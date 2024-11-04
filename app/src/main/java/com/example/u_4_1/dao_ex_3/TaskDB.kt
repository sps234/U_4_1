package com.example.u_4_1.dao_ex_3

import androidx.room.Database
import androidx.room.RoomDatabase

@Database( entities = [ TaskModel::class ], version = 1 )
abstract class TaskDB : RoomDatabase() {
    abstract fun taskDao() : TaskDao
}