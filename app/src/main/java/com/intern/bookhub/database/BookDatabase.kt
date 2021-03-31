package com.intern.bookhub.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.intern.bookhub.database.BookDao as DatabaseBookDao

@Database(entities = [BookEntity::class], version = 1)
abstract class BookDatabase :RoomDatabase(){

    abstract fun bookDao(): DatabaseBookDao



}