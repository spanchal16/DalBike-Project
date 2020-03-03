package com.CSCI5708.dalbike

import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*


class DetailBikeInfo : AppCompatActivity() {


    lateinit var ref: DatabaseReference
    lateinit var bikeName : TextView
    lateinit var bikeDescription : TextView
    lateinit var bikeImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main)

        ref = FirebaseDatabase.getInstance().getReference("bikes")

        bikeName = findViewById(R.id.bikeName)
        bikeDescription = findViewById(R.id.textView3)

        //val bikeImage : ImageView = findViewById(R.id.bikeImage)
        bikeImage = findViewById(R.id.bikeImage_firebase)


        getBikeData()
        }

    private fun getBikeData() {

        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {

            }
            override fun onDataChange(p0: DataSnapshot?) {
                if(p0!!.exists()){

                    for(p in p0.children){
                        val biker = p.getValue(Bikes::class.java)
                        bikeName.setText(biker!!.bikeName)
                        bikeDescription.setText(biker!!.bikeDescription.toString())
                        Glide.with(getApplicationContext()).load(biker.bikeUrl).into(bikeImage);
                    }
                }
            }

        })
    }

}
