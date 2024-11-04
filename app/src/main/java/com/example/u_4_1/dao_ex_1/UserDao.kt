package com.example.u_4_1.dao_ex_1

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao  {

    @Insert
    suspend fun insert( user : Model_User )

    @Update
    suspend fun update( user : Model_User )

    @Delete
    suspend fun delete( user : Model_User )

    @Query( "select * from users ")
    fun getUsers() : LiveData<List<Model_User>>

}