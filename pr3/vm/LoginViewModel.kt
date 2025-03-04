package com.example.pr3.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.pr3.AppDatabase
import com.example.pr3.models.User
import com.example.pr3.repository.UserRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    val userLiveData: LiveData<User>

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        userLiveData = repository.userLiveData
    }

    fun login(email: String, password: String) {
        // Логика авторизации
    }
}