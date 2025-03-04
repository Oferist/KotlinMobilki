package com.example.pr3.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.pr3.dao.UserDao
import com.example.pr3.models.User

class UserRepository(private val userDao: UserDao) {

    val userLiveData = MutableLiveData<User>()

    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    fun getUser(id: Int): LiveData<User> {
        return liveData {
            val user = userDao.getUser(id)
            emit(user)
        }
    }
}
