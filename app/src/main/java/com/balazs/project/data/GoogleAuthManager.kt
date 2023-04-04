/*
package com.balazs.project.data

import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.balazs.project.data.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class GoogleAuthManager(private val googleSignInClient: GoogleSignInClient) {

    fun signIn(): LiveData<Result<User>> {
        val result = MutableLiveData<Result<User>>()

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

        return result
    }

    fun handleSignInResult(task: Task<GoogleSignInAccount>): Result<User> {
        val result = task.getResult(ApiException::class.java)
        val user = User(result.id, result.displayName, result.email)
        return Result.Success(user)
    }

    companion object {
        const val RC_SIGN_IN = 9001
    }
}
*/
