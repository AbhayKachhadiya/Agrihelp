package com.example.agrihelp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavigationActivity : AppCompatActivity() {

    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var homeFragment: HomeFragment
    private lateinit var contactUsFragment: ContactUsFragment
    private lateinit var bookingFragment: BookingFragment
    private lateinit var bookingHistoryFragment: BookingHistoryFragment
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        bottomNavView = findViewById(R.id.bnNavigation)
        bottomNavView.setOnItemSelectedListener(::bottomNavItemSelected)

        homeFragment = HomeFragment()
        contactUsFragment = ContactUsFragment()
        bookingFragment = BookingFragment()
        bookingHistoryFragment = BookingHistoryFragment()

        showHomeFragment()

        findViewById<ImageView>(R.id.icLogout).setOnClickListener{
            logOut()
        }
    }

    private fun logOut() {

        sharedPref = getSharedPreferences("agrihelp", MODE_PRIVATE)

        val spEditor = sharedPref.edit()
        spEditor.remove("Email")
        spEditor.apply()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


private fun bottomNavItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
        R.id.navHome -> showHomeFragment()
        R.id.navContactUs -> showContactUsFragment()
        R.id.navBooking -> showBookingFragment()
        R.id.navBookingHistory -> showBookingHistoryFragment()
        else -> return false
    }
    return true
}

private fun showHomeFragment() {
    supportFragmentManager.beginTransaction().apply {
        replace(R.id.fragmemtContainer, homeFragment)
        commit()
    }
}

private fun showContactUsFragment() {
    supportFragmentManager.beginTransaction().apply {
        replace(R.id.fragmemtContainer, contactUsFragment)
        commit()
    }
}

private fun showBookingFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmemtContainer, bookingFragment)
            commit()
        }
}

    private fun showBookingHistoryFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmemtContainer, bookingHistoryFragment)
            commit()
        }
    }
}