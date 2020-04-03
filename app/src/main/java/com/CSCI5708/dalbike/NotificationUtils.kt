package com.CSCI5708.dalbike

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.icu.text.CaseMap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import kotlin.random.Random

/**
 * Shows all Notification to the user.
 * Takes context as the argument.
 */
fun showNotification(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        var channel = notificationManager.getNotificationChannel(NOTIFICATION_SERVICE)
        if(channel == null) {
            channel = createChannel(context)
        }

        var notifications = getAllNotifications(context, channel)
        for (builder in notifications) {
            with(NotificationManagerCompat.from(context)) {
                notify(Random.nextInt(), builder.build())
            }
        }

    }
}

/**
 * Creates Channel if not exists.
 * Takes Context as an argument.
 */
@RequiresApi(Build.VERSION_CODES.O)
fun createChannel(context: Context): NotificationChannel? {
    //  Create the NotificationChannel
    val name = "DEFAULT"
    val descriptionText = "Notification Channel"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val mChannel = NotificationChannel("1", name, importance)
    mChannel.description = descriptionText
    // Register the channel with the system; you can't change the importance
    // or other notification behaviors after this
    val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(mChannel)
    return mChannel;
}


/**
 * Creates all the notifications
 * Takes context and channel as inputs
 * returns list of all notifications(Builders).
 */
@RequiresApi(Build.VERSION_CODES.O)
fun getAllNotifications(context: Context, channel: NotificationChannel): List<NotificationCompat.Builder>{
    var notifications = mutableListOf<NotificationCompat.Builder>()
    var builder = createNotification(
        context, channel, "Bike Reminder",
        "You have to return bike tomorrow.")
    notifications.add(builder)

    notifications.add(createNotification(context, channel, "Next Rental Update",
    "You are eligible to get next bike on April 11, 2020"))

    notifications.add(createNotification(context, channel, "COVID-19 Alert!!!",
        "Stay safe and Maintain Social Distancing"))

    return notifications
}

/**
 * Creates a notification for given title and text.
 * Takes context and channel as input along with title and text.
 */
@RequiresApi(Build.VERSION_CODES.O)
fun createNotification(context: Context, channel: NotificationChannel, title: String, text: String): NotificationCompat.Builder {
    var builder = NotificationCompat.Builder(context, channel.id)
        .setSmallIcon(R.drawable.ic_directions_bike_black_24dp)
        .setContentTitle(title)
        .setContentText(text)
    return builder
}