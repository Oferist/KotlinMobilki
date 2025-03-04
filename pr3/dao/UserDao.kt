package com.example.pr3.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pr3.models.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUser(id: Int): User
}