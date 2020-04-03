package com.CSCI5708.dalbike.model

//Bike model for storing bike details
class Bikes(val bikeId:Int, val bikeName:String, val bikeDescription: String, val bikeUrl:String) {

    constructor(): this( -1,"","",""){}
}