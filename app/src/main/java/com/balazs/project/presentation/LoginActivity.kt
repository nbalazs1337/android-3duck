package com.balazs.project.presentation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.balazs.project.R
import com.balazs.project.utils.TransparentStatusBarHandler
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginVM
    private lateinit var googleSignInClient: GoogleSignInClient

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK){
                viewModel.handleSignInResult(result.data)
            }
            else {
                viewModel.errorLiveData.postValue("Sign in failed")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TransparentStatusBarHandler.initTransparentStatusBar(window)
        setContentView(R.layout.activity_log_in)

        val txt_signUp : TextView = findViewById(R.id.txt_signin)
        viewModel = ViewModelProvider(this).get(LoginVM::class.java)
        viewModel.initGoogleSignIn()

        findViewById<Button>(R.id.button_register).setOnClickListener {
            val email = findViewById<EditText>(R.id.et_email).text.toString()
            val pass = findViewById<EditText>(R.id.et_pass_enter).text.toString()
            viewModel.signInWithEmail(email, pass)

        }

        findViewById<Button>(R.id.button_google).setOnClickListener {
            viewModel.signInWithGoogle(this, launcher)
            Toast.makeText(this,"test",Toast.LENGTH_SHORT).show()
        }


        viewModel.userLiveData.observe(this, Observer { user ->
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("email", user.email)
            intent.putExtra("name", user.name)
            startActivity(intent)
        })

        viewModel.errorLiveData.observe(this, { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()})

    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }






    }

