package com.balazs.project.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
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

        val title:String = intent.getStringExtra("title").toString()
        val name:String = intent.getStringExtra("name").toString()
        val price:String = intent.getStringExtra("price").toString()

        Log.d("sent_data", "${title} ${name}, ${price}")


        val sharedPreferences = getSharedPreferences("MyPrefsPhone", Context.MODE_PRIVATE)
        var phoneNumber = sharedPreferences.getString("phone" ,"").toString()
        var experience = sharedPreferences.getString("experience" ,"").toString()
        Log.d("sent_data", "${phoneNumber}, ${experience}}")

        findViewById<TextView?>(R.id.tv_address).text = title
        findViewById<TextView?>(R.id.txt_price).text = price
        findViewById<TextView?>(R.id.tv_name).text = name
        findViewById<TextView?>(R.id.txt_experience).text = experience
        findViewById<TextView?>(R.id.txt_phone).text = phoneNumber





    }
}