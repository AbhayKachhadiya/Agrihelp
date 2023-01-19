package com.example.agrihelp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavigationActivity : AppCompatActivity() {

    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var homeFragment: HomeFragment
    private lateinit var contactUsFragment: ContactUsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        bottomNavView = findViewById(R.id.bnNavigation)
        bottomNavView.setOnItemSelectedListener (::bottomNavItemSelected)

        homeFragment = HomeFragment()
        contactUsFragment = ContactUsFragment()

        showHomeFragment()
    }

    private fun bottomNavItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.navHome -> showHomeFragment()
            R.id.navContactUs -> showContactUsFragment()
            else -> return false
        }
        return true
    }

    private fun showHomeFragment()
    {
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fragmemtContainer, homeFragment)
            commit()
        }
    }

    private fun showContactUsFragment()
    {
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fragmemtContainer, contactUsFragment)
            commit()
        }
    }
}