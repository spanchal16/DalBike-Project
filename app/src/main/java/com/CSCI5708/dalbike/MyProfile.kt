package com.CSCI5708.dalbike

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.CSCI5708.dalbike.model.LoggedInUserModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_myprofile.*

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

        logoutBut.setOnClickListener(){
            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putBoolean("is_user_logged_in",false)
            editor.apply()
            editor.commit()
            LoggedInUserModel.loggedInUser=""
            intent = Intent(applicationContext, loginview::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

        }

    }

    fun getUserProfileData(){
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }
            override fun onDataChange(p0: DataSnapshot?) {
                if(p0!!.exists()){
                    for(p in p0.children){
                        val userProfile = p.getValue(UserProfileDetails::class.java)

                        tvTotalRides.setText("Total rides: " +  userProfile!!.totalRides.toString())
                        tvFineDue.setText("Fine due: " + userProfile!!.fineDues.toString())
                        tvLoanDate.setText("Bike borrowed on: " + userProfile!!.nextLoadDate)
                        tvDueDate.setText("Due date: " + userProfile!!.dueDate)
                        tvName.setText("User name: " + userProfile!!.userName)
                        tvCurrentBike.setText("Bike type: " + userProfile!!.bikeType)
                    }
                }
            }
        })
    }
}
