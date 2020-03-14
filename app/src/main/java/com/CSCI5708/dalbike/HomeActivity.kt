package com.CSCI5708.dalbike

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.CSCI5708.dalbike.R
import com.CSCI5708.dalbike.viewPageAdapter

class HomeActivity : AppCompatActivity() {

    internal lateinit var viewPager : ViewPager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_activity)

        viewPager = findViewById<View>(R.id.viewPager) as ViewPager
        val adapter= viewPageAdapter(this)
        viewPager.adapter = adapter
    }
}
