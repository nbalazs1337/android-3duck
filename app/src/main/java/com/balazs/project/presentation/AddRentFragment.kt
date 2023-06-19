package com.balazs.project.presentation

import android.app.Activity
import com.balazs.project.R


import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.balazs.project.MyFirebaseMessagingService
import com.balazs.project.data.model.rv.RentListing
import com.google.firebase.auth.FirebaseAuth


class AddRentFragment : DialogFragment() {


    companion object {
        private const val GALLERY_REQUEST_CODE = 1001
    }
    private val selectedPhotos: MutableList<String> = mutableListOf()
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
        val button = dialogView.findViewById<Button>(R.id.btn_add_rent)
        button.setOnClickListener {
            openGallery()
        }


        builder.setView(dialogView)
            //.setTitle("Enter Text")
            .setPositiveButton("Add Rent") { _, _ ->

                val user = FirebaseAuth.getInstance().currentUser
                // Send push notification
                val title_notification = "${user} just added a New Rent!"
                val message = "Check it out!"

                val notificationService = MyFirebaseMessagingService()
                notificationService.generateNotification(requireContext(), title_notification, message)


                val title = dialogView.findViewById<EditText>(R.id.et_title).text.toString()
                val cartier = dialogView.findViewById<EditText>(R.id.et_cartier).text.toString()
                val street = dialogView.findViewById<EditText>(R.id.et_street).text.toString()
                val number = dialogView.findViewById<EditText>(R.id.et_number).text.toString()
               // val photo = dialogView.findViewById<EditText>(R.id.et_floor).text.toString()


                val description = dialogView.findViewById<EditText>(R.id.et_description).text.toString()
                val sharedPrefsDescription = requireContext().getSharedPreferences("MyPrefsDescription", Context.MODE_PRIVATE)
                sharedPrefsDescription.edit().putString("description", description).apply()


                val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("title", title)
                editor.putString("cartier", cartier)
                editor.putString("street", street)
                editor.putString("number", number)
                //editor.putString("floor", floor)
                editor.putString("description", description)
                editor.apply()
                val rentListing = RentListing(title,cartier,street, number, description,selectedPhotos )
                addRentListener?.onRentAdded(rentListing)
            }
            .setNegativeButton("Cancel", null)

        return builder.create()
    }

    interface AddRentListener {
        fun onRentAdded(rentListing: RentListing)

    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            Log.d("photo", "From Add Rent Fragment ${selectedImageUri}")
            if (selectedImageUri != null) {
                selectedPhotos.add(selectedImageUri.toString())
                Log.d("photo", "From Add Rent Fragment ${selectedPhotos}")


            }
        }
    }




}