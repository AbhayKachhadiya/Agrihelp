package com.example.agrihelp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.agrihelp.adapters.ImageAdapter

class HomeFragment : Fragment() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: Handler
    private lateinit var imageList:ArrayList<Int>
    private lateinit var adapter: ImageAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            init()
            setUpTransformer()

            viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    handler.removeCallbacks(runnable)
                    handler.postDelayed(runnable, 3000)
                }
            })
        }

    override fun onPause() {
        super.onPause()

        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()

        handler.postDelayed(runnable, 3000)
    }

    private  val runnable = Runnable{
        viewPager2.currentItem = viewPager2.currentItem + 1
    }
    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(20))

        viewPager2.setPageTransformer(transformer)
    }

    private fun init() {

        viewPager2 = requireView().findViewById(R.id.vpMachine)
        handler = Handler(Looper.myLooper()!!)
        imageList = ArrayList()

        imageList.add(R.drawable.dronedisplay)
        imageList.add(R.drawable.harvester2)
        imageList.add(R.drawable.thresher1)
        imageList.add(R.drawable.tractor)

        adapter = ImageAdapter(imageList, viewPager2)

        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 4

        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

    }
}
