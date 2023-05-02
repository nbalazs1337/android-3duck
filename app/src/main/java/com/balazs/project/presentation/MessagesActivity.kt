package com.balazs.project.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.balazs.project.R
import com.balazs.project.utils.TransparentStatusBarHandler

class MessagesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)
        TransparentStatusBarHandler.initTransparentStatusBar(window)
        val btn_back : ImageButton = findViewById(R.id.btn_back_messages)
        btn_back.setOnClickListener {
            finish()
        }
    }
}