package com.example.agrihelp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agrihelp.R
import com.example.agrihelp.models.OnBoarding

class OnBoardingAdapter(private val onboardingItem: List<OnBoarding>) :
    RecyclerView.Adapter<OnBoardingAdapter.OnBoardingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return OnBoardingItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.onboarding_item_container,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.bind(onboardingItem[position])
    }

    override fun getItemCount(): Int {
        return onboardingItem.size
    }

    inner class OnBoardingItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imageOnBoarding = view.findViewById<ImageView>(R.id.imgOnBoarding)
        private val txtTitle = view.findViewById<TextView>(R.id.txtTitle)
        private val txtDescription = view.findViewById<TextView>(R.id.txtDescription)

        fun bind(onboardingItem: OnBoarding) {
            imageOnBoarding.setImageResource(onboardingItem.onboardingImage)
            txtTitle.text = onboardingItem.title
            txtDescription.text = onboardingItem.description
        }
    }
}