package com.example.agrihelp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

@Suppress("DEPRECATION")
class ProfileFragment : Fragment() {

    private lateinit var txtBookingHistory: TextView
    private lateinit var txtSupport: TextView
    private lateinit var txtAboutAgrihelp: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        txtBookingHistory = view.findViewById(R.id.txtBookingHistory)
        txtBookingHistory.setOnClickListener{

            val fragment = BookingHistoryFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.profileContainer, fragment)?.commit()
        }

        txtSupport = view.findViewById(R.id.txtSupport)
        txtSupport.setOnClickListener{

            val fragment = ContactUsFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.profileContainer, fragment)?.commit()
        }

        txtAboutAgrihelp = view.findViewById(R.id.txtAboutAgrihelp)
        txtAboutAgrihelp.setOnClickListener{

            val fragment = AboutUsFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.profileContainer, fragment)?.commit()
        }

        return view
    }
}