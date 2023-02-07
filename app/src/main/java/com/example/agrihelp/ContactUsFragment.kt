package com.example.agrihelp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.agrihelp.models.ContactUs
import com.example.agrihelp.services.AuthService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection

class ContactUsFragment : Fragment() {

    private lateinit var contactUs: ContactUs
    private lateinit var authService: AuthService
    private lateinit var txtFullName: EditText
    private lateinit var txtEmailAddress: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var txtMessage: EditText
    private lateinit var btnSubmit: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtFullName = view.findViewById(R.id.txtFullName)
        txtEmailAddress = view.findViewById(R.id.txtEmailAddress)
        phoneNumber = view.findViewById(R.id.txtPhoneNumber)
        txtMessage = view.findViewById(R.id.txtQuery)
        btnSubmit = view.findViewById(R.id.btnSubmit)

        btnSubmit.setOnClickListener{

            val name = txtFullName.text.toString()
            val emailaddress = txtEmailAddress.text.toString()
            val phonenumber = phoneNumber.text.toString()
            val message = txtMessage.text.toString()

            contactUs = ContactUs(
                name = name,
                email_address = emailaddress,
                mobile_no = phonenumber,
                message = message
            )
            ContactUs()
        }



    }

    private fun ContactUs(){
        CoroutineScope(Dispatchers.IO).launch {
            authService = AuthService()

            val Response = authService.contactUs(contactUs)
            if(Response.code == HttpURLConnection.HTTP_CREATED){
                CoroutineScope(Dispatchers.IO).launch {
                    Toast.makeText(
                        requireContext(),
                        "Your mesage has been sent",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }
        }

    }


}