package com.example.agrihelp

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agrihelp.adapters.ItemsAdapter
import com.example.agrihelp.models.BookingDetail
import com.example.agrihelp.services.BookingDetailService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection


class BookingHistoryFragment : Fragment() {

    private lateinit var bookingRecyclerView : RecyclerView
    private lateinit var bookingDetailService: BookingDetailService
    private lateinit var bookingList: ArrayList<BookingDetail>
    private lateinit var bookingAdapter: ItemsAdapter
    private lateinit var attachedContext: Activity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookingRecyclerView = view.findViewById(R.id.lstItems)
        displayBookingDetails()
    }

    fun displayBookingDetails(){

        val sharedprefs = this.requireActivity().getSharedPreferences("agrihelp", AppCompatActivity.MODE_PRIVATE)
        var loggedinUserId = sharedprefs.getInt("id",0)

        CoroutineScope(Dispatchers.IO).launch {

            bookingDetailService = BookingDetailService()
            val Response = bookingDetailService.getBookingDetails(loggedinUserId.toInt())

            if (Response.code == HttpURLConnection.HTTP_NOT_FOUND)
            {
                Toast.makeText(context,"No data Found",Toast.LENGTH_LONG).show()
                return@launch
            }

            bookingList = ArrayList()
            val bookingData = Gson().fromJson(Response.message,Array<BookingDetail>::class.java)
            withContext(Dispatchers.Main){
                for(bookingDetail in bookingData)
                {
                    bookingList.add(
                        BookingDetail(
                            service_name = bookingDetail.service_name,
                            actualHours = bookingDetail.actualHours,
                            cropName = bookingDetail.cropName,
                            status = bookingDetail.status,
                            bookingDate = bookingDetail.bookingDate
                            )
                    )
                    bookingAdapter = ItemsAdapter(
                        bookingList
                    )

                    bookingRecyclerView.layoutManager = GridLayoutManager(requireContext(),1)
                    bookingRecyclerView.adapter = bookingAdapter
                }
            }


        }


    }

}