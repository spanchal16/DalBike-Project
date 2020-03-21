package com.CSCI5708.dalbike

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.CSCI5708.dalbike.model.LoggedInUserModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home_activity.*
import kotlinx.android.synthetic.main.custom_layout.*
import kotlinx.android.synthetic.main.custom_layout.view.*

class HomeActivity : AppCompatActivity() {

    internal lateinit var recyclerView : RecyclerView
    public var loggedInUser:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_activity)


        recyclerView = findViewById<View>(R.id.viewPager) as RecyclerView

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

        with(viewPager) {
            recyclerView.adapter = BikeAdapter(context, this@HomeActivity::navigateToBooking).also {
                var list_bike = mutableListOf<Bikes>()
                var ref = FirebaseDatabase.getInstance().getReference("bikes")
                ref.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {

                    }
                    override fun onDataChange(p0: DataSnapshot?) {
                        if(p0!!.exists()){

                            for(p in p0.children){
                                val biker = p.getValue(Bikes::class.java)
                                if(biker != null)
                                    list_bike.add(biker)
                            }
                            it.bikes.addAll(list_bike)
                            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        }
                    }

                })
            }
        }

    }

    fun navigateToBooking(bike: Bikes) {
        var bookBike = Intent(this, BookBike::class.java)
        bookBike.putExtra("bikeId", bike.bikeId)
        bookBike.putExtra("bikeName", bike.bikeName)
        startActivity(bookBike)
    }




}
