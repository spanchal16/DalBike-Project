package com.CSCI5708.dalbike

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.CSCI5708.dalbike.model.LoggedInUserModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home_activity.*


class HomeActivity : AppCompatActivity() {

    internal lateinit var recyclerView : RecyclerView
    public var loggedInUser:String = ""
    private val sharedPrefFile = "kotlinsharedpreference"
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

        showNotification(this)
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_qr -> {

            val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
                Context.MODE_PRIVATE)
            val isUserLoggedIn = sharedPreferences.getBoolean("is_user_logged_in",false)

            if (!isUserLoggedIn) {
                Toast.makeText(applicationContext,"Please login first to register a bike",Toast.LENGTH_SHORT).show()
                intent = Intent(this, loginview::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }else {
                val intent = Intent(this, CameraActivity::class.java)
                startActivity(intent)
            }
            true
        }
        R.id.action_logout -> {
            val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
                Context.MODE_PRIVATE)
            sharedPreferences.edit().remove("is_user_logged_in").commit()
            Toast.makeText(applicationContext,"User logged out",Toast.LENGTH_SHORT).show()
            finish();
            startActivity(getIntent());
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    fun navigateToBooking(bike: Bikes) {
        var bookBike = Intent(this, BookBike::class.java)
        bookBike.putExtra("bikeId", bike.bikeId)
        bookBike.putExtra("bikeName", bike.bikeName)
        startActivity(bookBike)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.button_action_bar, menu);
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        val isUserLoggedIn = sharedPreferences.getBoolean("is_user_logged_in",false)

        if (isUserLoggedIn) {
            inflater.inflate(R.menu.button_action_logout, menu);
        }
        return true
    }



}
