package com.balazs.project.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import com.balazs.project.R
import com.balazs.project.utils.TransparentStatusBarHandler

class LandlordDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landlord_detail)
        TransparentStatusBarHandler.initTransparentStatusBar(window)

        val btn_back : ImageView = findViewById(R.id.back)
        btn_back.setOnClickListener {
            finish()
        }
    }
}