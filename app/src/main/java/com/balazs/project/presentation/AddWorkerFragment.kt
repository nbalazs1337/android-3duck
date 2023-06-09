package com.balazs.project.presentation


import com.balazs.project.R


import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.balazs.project.data.model.rv.RentListing
import com.balazs.project.data.model.rv.WorkerListing


class AddWorkerFragment : DialogFragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AddWorkerListener) {
            addWorkerListener = context
        }
    }
    override fun onDetach() {
        super.onDetach()
        addWorkerListener = null
    }


    var addWorkerListener: AddWorkerListener? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.add_worker, null)



        builder.setView(dialogView)
            //.setTitle("Enter Text")
            .setPositiveButton("Add job") { _, _ ->

                val service = dialogView.findViewById<EditText>(R.id.et_service).text.toString()
                val price = dialogView.findViewById<EditText>(R.id.et_price).text.toString()
                val city = dialogView.findViewById<EditText>(R.id.et_city).text.toString()
                val description = dialogView.findViewById<EditText>(R.id.et_description).text.toString()



                val sharedPreferences = requireContext().getSharedPreferences("MyPrefsWorker", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                editor.putString("service", service)
                editor.putString("price", price)
                editor.putString("city", city)
                editor.putString("description", description)
                editor.apply()
                val workerListing = WorkerListing(service,city, price, description)
                addWorkerListener?.onWorkerAdded(workerListing)
            }
            .setNegativeButton("Cancel", null)

        return builder.create()
    }

    interface AddWorkerListener {
        fun onWorkerAdded(workerListing: WorkerListing)


    }

}