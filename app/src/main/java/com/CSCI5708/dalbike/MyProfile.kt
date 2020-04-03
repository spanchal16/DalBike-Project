package com.CSCI5708.dalbike

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.CSCI5708.dalbike.model.LoggedInUserModel
import com.CSCI5708.dalbike.model.UserProfileDetails
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

//Class for MyProfile activity
class MyProfile : AppCompatActivity() {

    lateinit var ref: DatabaseReference
    lateinit var tvTotalRides : TextView
    lateinit var tvFineDue : TextView
    lateinit var tvLoanDate : TextView
    lateinit var tvDueDate : TextView
    lateinit var tvName : TextView
    lateinit var tvCurrentBike : TextView
    private val sharedPrefFile = "kotlinsharedpreference"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myprofile)
        //Shared preference for user information
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        val isUserLoggedIn = sharedPreferences.getBoolean("is_user_logged_in",false)

        if (!isUserLoggedIn) {
            intent = Intent(this, loginview::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        ref = FirebaseDatabase.getInstance().getReference("userProfileDetails")

        tvTotalRides = findViewById(R.id.tvTotalRides)
        tvFineDue = findViewById(R.id.tvFineDue)
        tvLoanDate = findViewById(R.id.tvLoanDate)
        tvDueDate = findViewById(R.id.tvDueDate)
        tvName = findViewById(R.id.tvName)
        tvCurrentBike = findViewById(R.id.tvCurrentBike)


        getUserProfileData()

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation);
        //Handling bottom navigation based on id of the button clicked
        bottomNavigation.setOnNavigationItemSelectedListener { item ->

            when(item.itemId){

                R.id.nav_bikes -> {
                    val intent = Intent(this,HomeActivity::class.java)
                    startActivity(intent)
                }

                R.id.nav_profile -> {
                    if(LoggedInUserModel.loggedInUser.equals("")){
                        val intent = Intent(this,loginview::class.java)
                        startActivity(intent)
                    }
                    else {
                        val intent = Intent(this, MyProfile::class.java)
                        //startActivity(intent)
                    }
                }

                R.id.nav_support -> {
                    val intent = Intent(this, SupportActivity::class.java)
                    startActivity(intent)
                }

            }
            true

        }

        /*logoutBut.setOnClickListener(){
            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putBoolean("is_user_logged_in",false)
            editor.apply()
            editor.commit()
            LoggedInUserModel.loggedInUser=""
            intent = Intent(applicationContext, loginview::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

        }*/

    }

    fun getUserProfileData(){
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }
            override fun onDataChange(p0: DataSnapshot?) {
                if(p0!!.exists()){
                    for(p in p0.children){
                        val userProfile = p.getValue(UserProfileDetails::class.java)
                        //Fetching and setting users details from firebase
                        tvTotalRides.setText("Total rides: " +  userProfile!!.totalRides.toString())
                        tvFineDue.setText("Fine due: " + userProfile!!.fineDues.toString())
                        tvLoanDate.setText("Bike borrowed on: " + userProfile!!.nextLoadDate)
                        tvDueDate.setText("Due date: " + userProfile!!.dueDate)
                        tvName.setText(userProfile!!.userName)
                        tvCurrentBike.setText("Bike type: " + userProfile!!.bikeType)
                    }
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_qr -> {

            val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
                Context.MODE_PRIVATE)
            val isUserLoggedIn = sharedPreferences.getBoolean("is_user_logged_in",false)

            if (!isUserLoggedIn) {
                Toast.makeText(applicationContext,"Please login first to register a bike", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(applicationContext,"User logged out", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent . FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //inflating menu
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
