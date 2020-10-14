package com.sample.demo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.demo.model.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}