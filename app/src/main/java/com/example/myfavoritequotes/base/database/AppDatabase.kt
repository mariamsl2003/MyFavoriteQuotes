package com.example.myfavoritequotes.base.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myfavoritequotes.base.database.daos.QuotesDao
import com.example.myfavoritequotes.base.module.Quotes

@Database(entities = [Quotes::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun quoteDao(): QuotesDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "quotes_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}