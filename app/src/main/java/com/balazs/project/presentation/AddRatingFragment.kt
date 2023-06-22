package com.balazs.project.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.RatingBar
import androidx.fragment.app.DialogFragment
import com.balazs.project.R


class AddRatingFragment : DialogFragment() {



    // Define a listener interface to handle the form submission
    interface AddRatingListener {
        fun onRatingSubmitted(
            punctualityRating: Float,
            kindnessRating: Float,
            pricePerWorkRating: Float,
            professionalismRating: Float
        )
    }

     var listener: AddRatingListener? = null
     var averageRating: Float = 0.0f

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.add_rating, null)
        averageRating = arguments?.getFloat("averageRating") ?: 0.0f
        val btnSubmit = view.findViewById<Button>(R.id.btnSubmit)
        val ratingBarPunctuality = view.findViewById<RatingBar>(R.id.ratingBarPunctuality)
        val ratingBarKindness = view.findViewById<RatingBar>(R.id.ratingBarKindness)
        val ratingBarPricePerWork = view.findViewById<RatingBar>(R.id.ratingBarPricePerWork)
        val ratingBarProfessionalism = view.findViewById<RatingBar>(R.id.ratingBarProfessionalism)

        btnSubmit.setOnClickListener {
            val punctualityRating = ratingBarPunctuality.rating
            val kindnessRating = ratingBarKindness.rating
            val pricePerWorkRating = ratingBarPricePerWork.rating
            val professionalismRating = ratingBarProfessionalism.rating

            listener?.onRatingSubmitted(
                punctualityRating,
                kindnessRating,
                pricePerWorkRating,
                professionalismRating
            )

            dismiss()
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Add Recommendation")
            .setView(view)
            .create()

        return dialog
    }

    // Set the listener when the dialog is attached to the parent activity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AddRatingListener) {
            listener = context
        }
    }

    // Reset the listener when the dialog is detached from the parent activity
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun setAddRatingListener(listener: AddRatingListener) {
        this.listener = listener
    }


}