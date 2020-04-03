package com.CSCI5708.dalbike.model

class UserProfileDetails(val bikeType:String,
                         val dueDate:String,
                         val nextLoadDate:String,
                         val fineDues:Long,
                         val totalRides:Long,
                         val userName:String) {

    constructor(): this("","","",0,0,""){}
}