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
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection

class LoginActivity : AppCompatActivity() {

    private lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedprefs = getSharedPreferences("agrihelp", MODE_PRIVATE)
        var Email = sharedprefs.getString("Email",null)

        authService = AuthService()

        if(Email != null) {
            startActivity(Intent(this,NavigationActivity::class.java))
        }
        val txtEmailAddress = findViewById<EditText>(R.id.txtEmailAddress)
        val txtPassword = findViewById<EditText>(R.id.txtPassword)
        val lblRegister = findViewById<TextView>(R.id.lblRegister)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val emailaddress = txtEmailAddress.text.toString()
            val password =  txtPassword.text.toString()
            val user = User(email = emailaddress, password = password)

            CoroutineScope(Dispatchers.IO).launch {
                val response = authService.login(user)
                if(response.code == HttpURLConnection.HTTP_OK)
                {
                    val loggedInUser = Gson().fromJson(response.message, User::class.java)
                    withContext(Dispatchers.Main){
                        val spEditor = sharedprefs.edit()
                        spEditor.putString("Email",emailaddress)
                        spEditor.apply()
                        navigate()
                    }
                }
                else if(response.code == HttpURLConnection.HTTP_NOT_FOUND)
                {
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@LoginActivity,"email or password",Toast.LENGTH_LONG).show()
                    }
                }
            }

        }




        lblRegister.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onResume(){
        super.onResume()
        val sharedprefs = getSharedPreferences("agrihelp", MODE_PRIVATE)
        var Email = sharedprefs.getString("Email",null)


        if(Email != null) {
            startActivity(Intent(this,NavigationActivity::class.java))
        }
    }

    private fun navigate(){
                val intent = Intent(this, NavigationActivity::class.java)
                startActivity(intent)
    }


}