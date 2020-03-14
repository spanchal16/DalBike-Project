package com.CSCI5708.dalbike

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.navigation.findNavController
import androidx.viewpager.widget.ViewPager
import com.CSCI5708.dalbike.R
import com.CSCI5708.dalbike.viewPageAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.custom_layout.*

class HomeActivity : AppCompatActivity() {

    internal lateinit var viewPager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_activity)


        viewPager = findViewById<View>(R.id.viewPager) as ViewPager

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener { item ->

            when(item.itemId){

                R.id.nav_bikes -> {
                    val intent = Intent(this,loginview::class.java)
                    startActivity(intent)
                }

                R.id.nav_profile -> {
                    val intent = Intent(this,MyProfile::class.java)
                    startActivity(intent)
                }

                R.id.nav_support -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }

            }
            true
        }

        val adapter= viewPageAdapter(this)
        viewPager.adapter = adapter

    }



}
