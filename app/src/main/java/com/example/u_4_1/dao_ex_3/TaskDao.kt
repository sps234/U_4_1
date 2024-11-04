package com.example.u_4_1.dao_ex_3

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {

    @Insert
    suspend fun insert( taskModel: TaskModel )

    @Delete
    suspend fun delete( taskModel: TaskModel )

    @Query( "select * from Tasks")
    fun getAllTasks() : LiveData< MutableList<TaskModel> >
}