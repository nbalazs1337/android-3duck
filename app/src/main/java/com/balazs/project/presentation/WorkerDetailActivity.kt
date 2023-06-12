package com.balazs.project.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.balazs.project.R
import com.balazs.project.utils.TransparentStatusBarHandler

class WorkerDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_detail)
        TransparentStatusBarHandler.initTransparentStatusBar(window)
        val btn_back : ImageView = findViewById(R.id.btn_back)
        btn_back.setOnClickListener {
            finish()
        }
    }
}