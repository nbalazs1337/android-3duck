package com.balazs.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.balazs.project.data.model.api.SearchResponse


class TestActivity : AppCompatActivity() {

    lateinit var viewModel: TestVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        viewModel = ViewModelProvider(this).get(TestVM::class.java)

        // Observe the property search result
        viewModel.propertySearchResult.observe(this, Observer { searchResponse ->
            // Display the fetched data in your UI
            displayData(searchResponse)
        })

        // Observe the error
        viewModel.error.observe(this, Observer { error ->
            // Handle the error, e.g., display an error message
            showError(error)
        })

        // Initiate the search request
        viewModel.searchProperties()
    }

    private fun displayData(searchResponse: SearchResponse?) {
        // Update your UI with the fetched data
        // For example, update TextView or RecyclerView with the searchResponse
        val resultTextView: TextView = findViewById(R.id.tv_result)

        if (searchResponse != null) {
            // Display the fetched data in the resultTextView
            val data = "Fetched data: ${searchResponse.toString()}"
            resultTextView.text = data
        } else {
            // If searchResponse is null, display a message indicating no data
            resultTextView.text = "No data found."
        }
    }

    private fun showError(error: String) {
        // Display the error message, e.g., show a Toast or update an error TextView
        val resultTextView: TextView = findViewById(R.id.tv_result)
        // Display the error message in the resultTextView
        resultTextView.text = "Error: $error"
    }
}
