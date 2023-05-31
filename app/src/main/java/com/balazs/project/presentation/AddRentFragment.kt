package com.balazs.project.presentation

import com.balazs.project.R


import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.balazs.project.data.model.rv.RentListing


class AddRentFragment : DialogFragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AddRentListener) {
            addRentListener = context
        }
    }
    override fun onDetach() {
        super.onDetach()
        addRentListener = null
    }


    var addRentListener: AddRentListener? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.add_rent, null)



        builder.setView(dialogView)
            //.setTitle("Enter Text")
            .setPositiveButton("Add Rent") { _, _ ->
                val title = dialogView.findViewById<EditText>(R.id.et_title).text.toString()
                val cartier = dialogView.findViewById<EditText>(R.id.et_cartier).text.toString()
                val street = dialogView.findViewById<EditText>(R.id.et_street).text.toString()
                val number = dialogView.findViewById<EditText>(R.id.et_number).text.toString()
                val floor = dialogView.findViewById<EditText>(R.id.et_floor).text.toString()
                val description = dialogView.findViewById<EditText>(R.id.et_description).text.toString()

                val rentListing = RentListing(title,cartier,street, number,floor, description)
                addRentListener?.onRentAdded(rentListing)
                // Pass the rentListing object back to the TenantFragment
               // val tenantFragment = parentFragment as? TenantFragment
                //tenantFragment?.addRentListing(rentListing)
            }
            .setNegativeButton("Cancel", null)

        return builder.create()
    }

    interface AddRentListener {
        fun onRentAdded(rentListing: RentListing)


    }

}