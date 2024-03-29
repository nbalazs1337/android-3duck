package com.balazs.project.presentation

import RatingManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.balazs.project.R
import com.balazs.project.data.model.RatingData
import com.balazs.project.utils.TransparentStatusBarHandler

class LandlordDetailActivity : AppCompatActivity() {
    private var ratingData: RatingData? = null
    private var reviewCount: Int = 0
    private var bigAverage: Float = 0.0f
    private var finalResult: Float = 0.0f
    private var formattedResult: Float = 0.0f
    private var averageRating: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landlord_detail)
        TransparentStatusBarHandler.initTransparentStatusBar(window)

        val title: String = intent.getStringExtra("title").toString()
        val name: String = intent.getStringExtra("name").toString()
        val price: String = intent.getStringExtra("price").toString()
        val itemId: String = intent.getStringExtra("itemId").toString()

        Log.d("idd", "${itemId}")

        val txt_rating_average: TextView = findViewById(R.id.txt_rating_average)
        val txt_rating_counter: TextView = findViewById(R.id.txt_rating_counter)


        averageRating = RatingManager.getAverageRating(this@LandlordDetailActivity, itemId)
        Log.d("savingRating", "${averageRating}")
        reviewCount = RatingManager.getReviewCount(this@LandlordDetailActivity, itemId)
        Log.d("savingRating", "${reviewCount}")

        txt_rating_average.text = averageRating.toString()
        txt_rating_counter.text = reviewCount.toString()


        val btn_review: Button = findViewById(R.id.btn_add_review)
        btn_review.setOnClickListener {
            val addRatingFragment = AddRatingFragment()
            addRatingFragment.setAddRatingListener(object : AddRatingFragment.AddRatingListener {
                override fun onRatingSubmitted(
                    punctualityRating: Float,
                    kindnessRating: Float,
                    pricePerWorkRating: Float,
                    professionalismRating: Float
                ) {
                    ratingData = RatingData(
                        punctualityRating,
                        kindnessRating,
                        pricePerWorkRating,
                        professionalismRating
                    )
                    reviewCount++
                    bigAverage = bigAverage + ratingData!!.calculateAverage()
                    finalResult = bigAverage / reviewCount
                    formattedResult = String.format("%.2f", finalResult).toFloat()
                    txt_rating_average.text = formattedResult.toString()
                    txt_rating_counter.text = reviewCount.toString()

                    Log.d("savingRating", "After saving${formattedResult}")
                    Log.d("savingRating", "After saving ${reviewCount}")

                    RatingManager.saveRatingData(this@LandlordDetailActivity, itemId, formattedResult, reviewCount)



                    // Save the average rating to SharedPreferences

                    Log.d("rating", "${itemId}")

                    // Retrieve the average rating and review count from SharedPreferences



                    // Use the rating data in the RentDetailActivity or LandlordFragment
                }
            })
            addRatingFragment.show(supportFragmentManager, "AddRatingFragment")
        }

        val btn_back: ImageView = findViewById(R.id.back)
        btn_back.setOnClickListener {
            finish()
        }



        Log.d("sent_data", "${title} ${name}, ${price}")


        val sharedPreferences = getSharedPreferences("MyPrefsPhone", Context.MODE_PRIVATE)
        var phoneNumber = sharedPreferences.getString("phone", "").toString()
        var experience = sharedPreferences.getString("experience", "").toString()
        Log.d("sent_data", "${phoneNumber}, ${experience}}")

        findViewById<TextView?>(R.id.tv_address).text = title
        findViewById<TextView?>(R.id.txt_price).text = price
        findViewById<TextView?>(R.id.tv_name).text = name
        findViewById<TextView?>(R.id.txt_experience).text = experience
        findViewById<TextView?>(R.id.txt_phone).text = phoneNumber


    }
}