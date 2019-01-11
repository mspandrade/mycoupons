package com.projectme.mpandrade.mycoupon.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.NotificationManager
import android.app.NotificationChannel
import android.os.Build
import android.media.RingtoneManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.projectme.mpandrade.mycoupon.MainActivity
import com.projectme.mpandrade.mycoupon.R


class MyCouponFireBaseMessageService : FirebaseMessagingService() {

    companion object {
        const val TAG = "MyCouponService"
    }

    override fun onNewToken(token: String?) {
        Log.d(TAG, "TOKEN: " + token ?: "vazio")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        val notification = remoteMessage?.notification

        if (notification != null) {
            sendNotification(remoteMessage.notification!!)
        }
    }

    private fun sendNotification(notification: RemoteMessage.Notification) {

        val intent = Intent(this, MainActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setContentTitle(notification.title)
                .setContentText(notification.body)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setLights(Color.CYAN, 3000, 3000)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(TAG, 2, notificationBuilder.build())
    }

}