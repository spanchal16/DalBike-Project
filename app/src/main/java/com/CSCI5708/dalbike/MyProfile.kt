package com.CSCI5708.dalbike

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

class MyProfile : AppCompatActivity() {

    lateinit var ref: DatabaseReference
    lateinit var tvTotalRides : TextView
    lateinit var tvFineDue : TextView
    lateinit var tvLoanDate : TextView
    lateinit var tvDueDate : TextView
    lateinit var tvName : TextView
    lateinit var tvCurrentBike : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myprofile)
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

    }

    fun getUserProfileData(){
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }
            override fun onDataChange(p0: DataSnapshot?) {
                if(p0!!.exists()){
                    for(p in p0.children){
                        val userProfile = p.getValue(UserProfileDetails::class.java)

                        tvTotalRides.setText(userProfile!!.totalRides.toString())
                        tvFineDue.setText(userProfile!!.fineDues.toString())
                        tvLoanDate.setText(userProfile!!.nextLoadDate)
                        tvDueDate.setText(userProfile!!.dueDate)
                        tvName.setText(userProfile!!.userName)
                        tvCurrentBike.setText(userProfile!!.bikeType)
                    }
                }
            }
        })
    }
}
