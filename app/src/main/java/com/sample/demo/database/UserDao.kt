package com.sample.demo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sample.demo.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(userList: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("DELETE FROM user")
    fun deleteTable()

    @Query("SELECT * FROM user")
    fun loadCountries(): List<User>

    @Query("SELECT * FROM user where id =:id")
    fun getCountryById(id: String): User
}