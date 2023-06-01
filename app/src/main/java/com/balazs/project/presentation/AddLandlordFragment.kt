package com.balazs.project.presentation

import com.balazs.project.data.model.rv.LandlordListing


import com.balazs.project.R


import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.balazs.project.data.model.rv.RentListing


class AddLandlordFragment : DialogFragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AddLandlordListener) {
            addLandlordListener = context
        }
    }
    override fun onDetach() {
        super.onDetach()
        addLandlordListener = null
    }


    var addLandlordListener: AddLandlordListener? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.add_landlord, null)



        builder.setView(dialogView)
            //.setTitle("Enter Text")
            .setPositiveButton("Add a Service") { _, _ ->
                val name = dialogView.findViewById<EditText>(R.id.et_name).text.toString()
                val service = dialogView.findViewById<EditText>(R.id.et_service).text.toString()
                val price = dialogView.findViewById<EditText>(R.id.et_price).text.toString()
                val experience = dialogView.findViewById<EditText>(R.id.et_exp).text.toString()
                val description = dialogView.findViewById<EditText>(R.id.et_description).text.toString()



                val sharedPreferences = requireContext().getSharedPreferences("MyPrefsLandlord", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("name", name)
                editor.putString("service", service)
                editor.putString("price", price)
                editor.putString("experience", experience)
                editor.putString("description", description)

                editor.apply()
                val landlordListing = LandlordListing(name, service, price, experience, description)
                addLandlordListener?.onLandlordAdded(landlordListing)
            }
            .setNegativeButton("Cancel", null)

        return builder.create()
    }

    interface AddLandlordListener {
        fun onLandlordAdded(landlordListing: LandlordListing)


    }

}