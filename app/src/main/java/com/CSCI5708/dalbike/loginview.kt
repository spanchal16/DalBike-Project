package com.CSCI5708.dalbike

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_loginview.*
import java.util.*


class loginview : AppCompatActivity() {

    lateinit var ref: DatabaseReference
    lateinit var bannerId : EditText
    lateinit var dpd: DatePickerDialog
    lateinit var dateText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginview)

        ref = FirebaseDatabase.getInstance().getReference("users")

        bannerId = findViewById(R.id.bannerId)
        dateText = findViewById(R.id.date1)

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

    }

    fun fundate (view:View){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        dpd = DatePickerDialog(this, android.R.style.Theme_Holo_Dialog, DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
            date1.text ="$dayOfMonth/$monthOfYear/$year"
        }, year, month, day)
        dpd.show()
    }


    fun checkCred(view: View){
        var enteredBanner:String = bannerId.text.toString()
        var enteredDOB = date1.text
        var bannerId:String = ""
        var Dob:String = ""

        //Check for user
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }
            override fun onDataChange(p0: DataSnapshot?) {
                if(p0!!.exists()){
                    for(p in p0.children){
                        val user = p.getValue(Users::class.java)
                        if(user!!.bannerId.equals(enteredBanner) && user!!.DOB.equals(enteredDOB)){
                            LoggedInUserModel.loggedInUser = "yes"
                            loginSucessfullLoginUser()
                            break;
                        }else{
                            val toast = Toast.makeText(applicationContext, "Wrong Banner ID or Date of Birth", Toast.LENGTH_SHORT)
                            toast.show()
                        }
                    }
                }
            }
        })

    }


    fun loginSucessfullLoginUser(){
        val intent = Intent(this, MyProfile::class.java)
        startActivity(intent)
    }

}
