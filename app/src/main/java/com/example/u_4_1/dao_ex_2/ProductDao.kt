package com.example.u_4_1.dao_ex_2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {

    @Insert
    suspend fun insert( product: Model_Product )

    @Update
    suspend fun update( product: Model_Product )

    @Delete
    suspend fun delete( product: Model_Product )

    @Query( "select * from Products")
    fun getAllProducts () : LiveData<List<Model_Product>>

}