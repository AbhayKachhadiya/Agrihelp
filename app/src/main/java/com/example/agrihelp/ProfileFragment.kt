package com.example.agrihelp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

@Suppress("DEPRECATION")
class ProfileFragment : Fragment() {

    private lateinit var txtSupport: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        txtSupport = view.findViewById(R.id.txtSupport)
        txtSupport.setOnClickListener{

            val fragment = ContactUsFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmemtContainer, fragment)?.commit()
        }

        return view
    }
}