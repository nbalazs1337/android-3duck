package com.balazs.project.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.balazs.project.R
import com.balazs.project.utils.TransparentStatusBarHandler

class WelcomeActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        TransparentStatusBarHandler.initTransparentStatusBar(window)
        val button : Button = findViewById<Button>(R.id.button_welcome)
        button.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val btn_home :Button = findViewById(R.id.btn_home)
        btn_home.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }}