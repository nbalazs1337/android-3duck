package com.balazs.project.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
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
        val sharedPreferences = getSharedPreferences("MyPrefsWorker", Context.MODE_PRIVATE)
        val service = sharedPreferences.getString("service", "") ?: ""
        val price = sharedPreferences.getString("price", "") ?: ""
        val city = sharedPreferences.getString("city", "") ?: ""
        val description = sharedPreferences.getString("description", "") ?: ""

        val txt_title = findViewById<TextView>(R.id.txt_title_worker_job)
        val txt_price = findViewById<TextView>(R.id.txt_price_worker_per_hour)
        val txt_city = findViewById<TextView>(R.id.textView)
        val txt_description = findViewById<TextView>(R.id.textView4)

        txt_title.text = service.toString()
        txt_price.text = price.toString()
        txt_city.text = city.toString()
        txt_description.text = description.toString()



    }
}