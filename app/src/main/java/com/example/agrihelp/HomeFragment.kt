package com.example.agrihelp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class HomeFragment : Fragment() {

    private lateinit var btnBookNowDroneSprayer : Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnBookNowDroneSprayer = view.findViewById(R.id.btnBookNowHarvester)
        btnBookNowDroneSprayer.setOnClickListener {
            val intent = Intent(requireContext(), BookingFragment::class.java)
            startActivity(intent)
        }
    }
}