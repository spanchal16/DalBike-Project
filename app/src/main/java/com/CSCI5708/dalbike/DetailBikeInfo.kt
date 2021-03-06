package com.CSCI5708.dalbike

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.CSCI5708.dalbike.model.Bikes
import com.CSCI5708.dalbike.model.LoggedInUserModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_dal_detail_bike_info.*

//Class for detailbike activity
class DetailBikeInfo : AppCompatActivity() {

    //firebase database reference
    lateinit var ref: DatabaseReference
    lateinit var bikeName : TextView
    lateinit var bikeDescription : TextView
    lateinit var bikeImage: ImageView
    var bikeNumber: Int = 0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_dal_detail_bike_info)

        bikeNumber = intent.getStringExtra("bikeName").toInt()

        ref = FirebaseDatabase.getInstance().getReference("bikes")

        bikeName = findViewById(R.id.bikeName)
        bikeDescription = findViewById(R.id.textView3)

        //val bikeImage : ImageView = findViewById(R.id.bikeImage)
        bikeImage = findViewById(R.id.bikeImage_firebase)

        //fetch data from firebase
        getBikeData(bikeNumber)

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
        checkAvailabilityButton.setOnClickListener {
            var bookBike = Intent(this, BookBike::class.java)
            bookBike.putExtra("bikeId", bikeNumber)
            bookBike.putExtra("bikeName", bikeName.text)
            startActivity(bookBike)
        }
    }

    private fun getBikeData(bikeNumber:Int) {
        //Fetching data from firebase
        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {

            }
            override fun onDataChange(p0: DataSnapshot?) {
                if(p0!!.exists()){
                    //populate the bike data to bike object
                    for(p in p0.children){
                        val biker = p.getValue(Bikes::class.java)
                        if(biker!!.bikeId == bikeNumber) {
                            bikeName.setText(biker!!.bikeName)
                            bikeDescription.setText(biker!!.bikeDescription.toString())
                            Glide.with(getApplicationContext()).load(biker.bikeUrl)
                                .into(bikeImage);
                        }
                    }
                }
            }

        })
    }

}
