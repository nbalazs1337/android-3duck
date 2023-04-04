package com.balazs.project.presentation

import android.graphics.ColorSpace
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.balazs.project.data.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class HomeViewModel:ViewModel() {

    private lateinit var googleSignInClient: GoogleSignInClient

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun setUser(newUser: User) {
        _user.postValue(newUser)
    }
}