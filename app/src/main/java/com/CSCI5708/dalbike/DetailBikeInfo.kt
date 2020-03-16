package com.CSCI5708.dalbike

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*


class DetailBikeInfo : AppCompatActivity() {


    lateinit var ref: DatabaseReference
    lateinit var bikeName : TextView
    lateinit var bikeDescription : TextView
    lateinit var bikeImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_dal_detail_bike_info)

        val bikeNumber :Int = intent.getStringExtra("bikeName").toInt()

        ref = FirebaseDatabase.getInstance().getReference("bikes")

        bikeName = findViewById(R.id.bikeName)
        bikeDescription = findViewById(R.id.textView3)

        //val bikeImage : ImageView = findViewById(R.id.bikeImage)
        bikeImage = findViewById(R.id.bikeImage_firebase)


        getBikeData(bikeNumber)

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

    }

    private fun getBikeData(bikeNumber:Int) {

        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {

            }
            override fun onDataChange(p0: DataSnapshot?) {
                if(p0!!.exists()){

                    for(p in p0.children){
                        val biker = p.getValue(Bike::class.java)
                        if(biker!!.bikeId == bikeNumber) {
                            bikeName.setText(biker!!.bikeName)
                            bikeDescription.setText(biker!!.bikeDescription.toString())
//                            Glide.with(getApplicationContext()).load(biker.bikeUrl)
//                                .into(bikeImage);
                        }
                    }
                }
            }

        })
    }

}
