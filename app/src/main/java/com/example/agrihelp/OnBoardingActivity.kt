package com.example.agrihelp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.example.agrihelp.adapters.OnBoardingAdapter
import com.example.agrihelp.models.OnBoarding

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var onboardingAdapter: OnBoardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        setOnBoardingItem()
    }

    private fun setOnBoardingItem() {
        onboardingAdapter = OnBoardingAdapter(
            listOf(
                OnBoarding(
                    onboardingImage = R.drawable.drone,
                    title = "Book It Online",
                    description = "Book your machinery online at rental price on rent for as many hours you want."
                ),

                OnBoarding(
                    onboardingImage = R.drawable.customer,
                    title = "24/7 Support",
                    description = "If any query arise regarding anything related to us just contact us you get 24/7 support."
                ),

                OnBoarding(
                    onboardingImage = R.drawable.grow,
                    title = "Grow With Us",
                    description = "Boost your yields by using our machinery and make your efforts to the market."
                )
            )
        )

        val getStarted = findViewById<Button>(R.id.btnGetStarted)
        getStarted.setOnClickListener{

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val onboardingViewPager = findViewById<ViewPager2>(R.id.viewPager)
        onboardingViewPager.adapter = onboardingAdapter
    }
}