package com.CSCI5708.dalbike

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.CSCI5708.dalbike.model.LoggedInUserModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_loginview.*
import java.util.*


class loginview : AppCompatActivity() {

    lateinit var ref: DatabaseReference
    lateinit var bannerId : EditText
    lateinit var dpd: DatePickerDialog
    lateinit var dateText: TextView
    private val sharedPrefFile = "kotlinsharedpreference"

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
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)
        var enteredBanner:String = bannerId.text.toString()
        var enteredDOB = date1.text
        var flag = -1
        var bannerId:String = ""
        var Dob:String = ""

        //Check for user
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }
            override fun onDataChange(p0: DataSnapshot?) {
                val editor:SharedPreferences.Editor =  sharedPreferences.edit()

                if(p0!!.exists()){
                    for(p in p0.children){
                        val user = p.getValue(Users::class.java)
                        if(user!!.bannerId.equals(enteredBanner) && user!!.DOB.equals(enteredDOB)){
                            System.out.println("done")
                            flag = 1
                            editor.putBoolean("is_user_logged_in",true)
                            editor.apply()
                            editor.commit()
                            LoggedInUserModel.loggedInUser = "yes"
                            loginSucessfullLoginUser()
                            break
                        }else{
                            System.out.println("error")
                            editor.putBoolean("is_user_logged_in",false)
                            editor.apply()
                            editor.commit()
                            val toast = Toast.makeText(applicationContext, "Wrong Banner ID or Date of Birth", Toast.LENGTH_SHORT)
                            toast.show()
                        }
                    }
                }
                if (flag==1){
                    intent = Intent(applicationContext, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }

            }
        })

    }


    fun loginSucessfullLoginUser(){
        val intent = Intent(this, MyProfile::class.java)
        startActivity(intent)
    }



}
