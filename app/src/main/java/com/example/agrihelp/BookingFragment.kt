package com.example.agrihelp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import java.util.*
import kotlin.collections.ArrayList


@Suppress("NAME_SHADOWING")
class BookingFragment : Fragment(), AdapterView.OnItemSelectedListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_booking, container, false)
    }

    private lateinit var dateEdt: EditText
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = ArrayList<SlideModel>() // Create image list

        imageList.add(SlideModel(R.drawable.harvester))
        imageList.add(SlideModel(R.drawable.drone_sprayer))
        imageList.add(SlideModel(R.drawable.thresh))

        val imageSlider = view.findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)

        showDatePicker()
    }

    private fun showDatePicker() {
        dateEdt = requireView().findViewById(R.id.idEdtDate)

        dateEdt.setOnClickListener {

            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, _, monthOfYear, dayOfMonth ->

                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    dateEdt.setText(dat)
                },

                year,
                month,
                day
            )

            datePickerDialog.show()
        }

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {

        val spinner: Spinner = requireView().findViewById(R.id.spiSelectMachinery)

        val services = arrayOf("Drone Sprayer", "Harvester", "Thresher")

        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line,services)

        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = this

        }
    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}