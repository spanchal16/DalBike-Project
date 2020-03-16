package com.CSCI5708.dalbike

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_myprofile.*

class MyProfile : Fragment() {

    lateinit var ref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_myprofile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ref = FirebaseDatabase.getInstance().getReference("userProfileDetails")
        getUserProfileData()
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
