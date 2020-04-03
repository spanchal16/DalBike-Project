package com.CSCI5708.dalbike.model


// User object to store user information
object LoggedInUserModel {
    var loggedInUser:String = ""
    get() = field
    set(value) { field = value }
}