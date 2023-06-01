package com.balazs.project.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.balazs.project.R
import com.balazs.project.utils.TransparentStatusBarHandler

class RentDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_detail)
        TransparentStatusBarHandler.initTransparentStatusBar(window)

        val btn_back : ImageButton = findViewById(R.id.btn_back_rentDetail)
        btn_back.setOnClickListener {
            finish()
        }
        val getTitle = intent.getStringExtra("title")
        val getCity = intent.getStringExtra("city")
        val getPrice = intent.getStringExtra("price")
        val title : TextView = findViewById(R.id.tv_item_name)
        val city : TextView = findViewById(R.id.tv_item_name)
        title.text = getTitle

    }
}