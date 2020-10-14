package com.sample.demo.database

import android.content.Context
import androidx.room.Room

class RoomDatabase {
    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE
            ?: synchronized(this) {
                INSTANCE ?: getRoomDatabase(context).also { INSTANCE = it }
            }

        private fun getRoomDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "SampleDatabase")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }

}


