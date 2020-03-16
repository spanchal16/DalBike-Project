package com.CSCI5708.dalbike

class DummyTest {
    companion object{
        val bikeList = listOf<Bike>(
            Bike(
                bikeId =  1,
                bikeName = "First Bike",
                bikeDescription = "Description",
                bikeUrl =  "https://pedegoelectricbikes.ca/wp-content/uploads/2018/08/pedego-city-commuter-1.jpg"
            ),
            Bike(
                bikeId =  2,
                bikeName = "Second Bike",
                bikeDescription = "Description",
                bikeUrl =  "https://vader-prod.s3.amazonaws.com/1551115303-citizen-bike-1551115279.jpg"
            ),
            Bike(
                bikeId =  3,
                bikeName = "Third Bike",
                bikeDescription = "Description",
                bikeUrl =  "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/rad-power-rad-city-0126-tested-1554833677.jpg?crop=0.8888888888888888xw:1xh;center,top&resize=640:*"
            )
        )
    }
}