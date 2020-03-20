package com.CSCI5708.dalbike

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.CSCI5708.dalbike.model.LoggedInUserModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    internal lateinit var viewPager : ViewPager
    public var loggedInUser:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_activity)


        viewPager = findViewById<View>(R.id.viewPager) as ViewPager

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener { item ->

            when(item.itemId){

                R.id.nav_bikes -> {
                    val intent = Intent(this,HomeActivity::class.java)
                    //startActivity(intent)
                }


                R.id.nav_profile -> {
                    if(LoggedInUserModel.loggedInUser.equals("")){
                        val intent = Intent(this,loginview::class.java)
                        startActivity(intent)
                    }
                    else {
                        val intent = Intent(this, MyProfile::class.java)
                        startActivity(intent)
                    }
                }

                R.id.nav_support -> {
                    val intent = Intent(this, SupportActivity::class.java)
                    startActivity(intent)
                }

            }
            true
        }

        val adapter= viewPageAdapter(this)
        viewPager.adapter = adapter

    }



}
