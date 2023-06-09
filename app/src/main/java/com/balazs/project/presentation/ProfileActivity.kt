package com.balazs.project.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.balazs.project.R
import com.balazs.project.utils.TransparentStatusBarHandler

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        TransparentStatusBarHandler.initTransparentStatusBar(window)
        val btn_back : ImageButton = findViewById(R.id.btn_back)
        btn_back.setOnClickListener {
            finish()
        }
    }
}