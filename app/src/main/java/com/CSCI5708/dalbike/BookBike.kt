package com.CSCI5708.dalbike

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
    private val sharedPrefFile = "kotlinsharedpreference"


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_bike)
        FirebaseApp.initializeApp(this);
        setupView()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setupView() {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        var isLoggedIn = sharedPreferences.getBoolean("is_user_logged_in",false)
        if (!isLoggedIn) {
            book_button.text = "Login to Book"
            book_button.setOnClickListener {
                startActivity(Intent(this, loginview::class.java))
            }
        } else {
            book_button.setOnClickListener {
                bookBike(intent.getIntExtra("bikeId", -1))
            }
        }
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

    private fun bookBike(bikeId: Int) {
        startActivity(Intent(this, BikeBooked::class.java))
    }
}