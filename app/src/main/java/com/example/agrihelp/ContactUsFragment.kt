package com.example.agrihelp

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.agrihelp.models.ContactUs
import com.example.agrihelp.services.AuthService

class ContactUsFragment : Fragment() {

    private lateinit var contactUs: ContactUs
    private lateinit var authService: AuthService
    private lateinit var txtFullName: EditText
    private lateinit var txtEmailAddress: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var txtMessage: Message

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}