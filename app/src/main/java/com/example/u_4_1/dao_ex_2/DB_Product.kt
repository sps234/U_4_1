package com.example.u_4_1.dao_ex_2

import androidx.room.Database
import androidx.room.RoomDatabase

@Database( entities = [Model_Product::class ] , version = 1 )
abstract  class DB_Product : RoomDatabase() {
    abstract fun productDao() : ProductDao
}