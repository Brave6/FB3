package com.e.firebase.notification

import android.app.PendingIntent
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.e.firebase.R
import com.e.firebase.viewmodel.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService


const val channelId = "notification channel"
const val channelName = "com.e.firebase.notification"

class MessageNotif: FirebaseMessagingService()
{

    fun getRemoteView(title: String, message:String): RemoteViews
    {
        val remoteView = RemoteViews("com.e.firebase.notification", R.layout.notification)

        remoteView.setTextViewText(R.id.title,title)
        remoteView.setTextViewText(R.id.message,message)
        remoteView.setTextViewText(R.id.app_logo,R.drawable.notification)
        return
    }
        fun generateNotification(title: String, messaging: String)
    {
        val intent = Intent(this, MainActivity::class .java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT )

        var builder:NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_notif)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder =  builder.setContent(getRemoteView(title,message))
    }
}