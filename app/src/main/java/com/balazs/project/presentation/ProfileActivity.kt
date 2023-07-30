package com.balazs.project.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.balazs.project.R
import com.balazs.project.utils.TransparentStatusBarHandler
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        TransparentStatusBarHandler.initTransparentStatusBar(window)
        val btn_back : ImageButton = findViewById(R.id.btn_back)
        btn_back.setOnClickListener {
            finish()
        }
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email
        val name = user?.displayName

        val txt_name : TextView = findViewById(R.id.tv_name)
        val txt_email :TextView= findViewById(R.id.tv_email)

        txt_name.text = name
        txt_email.text = email


    }
}