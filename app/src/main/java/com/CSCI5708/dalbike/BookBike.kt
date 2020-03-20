package com.CSCI5708.dalbike

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.book_bike.*
import java.util.*


class BookBike: AppCompatActivity() {

    lateinit var ref: DatabaseReference;


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_bike)
        FirebaseApp.initializeApp(this);
        setupView()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setupView() {
        var bikeId = intent.getIntExtra("bikeId", -1)
        var bikeNameStr = intent.getStringExtra("bikeName")
        val c = Calendar.getInstance()
        var t = c.time
        calendarView.minDate = t.toInstant().toEpochMilli()
        c.add(Calendar.DATE, 6)
        t = c.time
        calendarView.maxDate = t.toInstant().toEpochMilli()
        ref = FirebaseDatabase.getInstance().getReference("bikes")
        bikeName.text = bikeNameStr
    }
}