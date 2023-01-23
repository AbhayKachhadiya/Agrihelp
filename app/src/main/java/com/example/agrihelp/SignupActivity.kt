package com.example.agrihelp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.agrihelp.models.User
import com.example.agrihelp.services.AuthService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection

class SignupActivity : AppCompatActivity() {

    private lateinit var user: User
    private lateinit var authService: AuthService
    private lateinit var txtemail: EditText
    private lateinit var txtmobile: EditText
    private lateinit var txtpassword: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        txtemail = findViewById(R.id.txtEmailAddress)
        txtmobile = findViewById(R.id.txtPhoneNumber)
        txtpassword = findViewById(R.id.txtPassword)
        btnRegister = findViewById(R.id.btnSignUp)

        btnRegister.setOnClickListener {

            val email = txtemail.text.toString()
            val password = txtpassword.text.toString()
            val mobilenumber = txtmobile.text.toString()

            user = User(
                email = email,
                mobilenumber = mobilenumber,
                password = password
            )
            Register()
        }


        val lblLogin = findViewById<TextView>(R.id.lblLogin)

        lblLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun Register()
    {
        CoroutineScope(Dispatchers.IO).launch {
            authService = AuthService()

            val Response = authService.register(user)
            if (Response.code == HttpURLConnection.HTTP_CONFLICT) {
                CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(this@SignupActivity,"User already exist!",Toast.LENGTH_LONG).show()
                startActivity(Intent(this@SignupActivity,LoginActivity::class.java))
                }
            }
            else if (Response.code == HttpURLConnection.HTTP_CREATED) {
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(
                        this@SignupActivity,
                        "User registered successfully!",
                        Toast.LENGTH_LONG
                    ).show()
                startActivity(Intent(this@SignupActivity,LoginActivity::class.java))
                }
            }
        }
    }

    private fun goToLogin()
    {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}

