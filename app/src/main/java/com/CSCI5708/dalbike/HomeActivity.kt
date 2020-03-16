package com.CSCI5708.dalbike

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home_activity.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_activity)
        Toast.makeText(baseContext, "Hello From Home Acrivity", Toast.LENGTH_SHORT).show()
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigation.setupWithNavController(Navigation.findNavController(this, R.id.nav_host))


    }



}
