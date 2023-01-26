package com.example.agrihelp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel


class BookingFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_booking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = ArrayList<SlideModel>() // Create image list

        imageList.add(SlideModel(R.drawable.harvester))
        imageList.add(SlideModel(R.drawable.drone_sprayer))
        imageList.add(SlideModel(R.drawable.thresh))

        val imageSlider = view.findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)
    }
}