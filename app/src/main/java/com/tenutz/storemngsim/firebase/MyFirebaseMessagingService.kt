package com.tenutz.storemngsim.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.application.MainActivity
import com.tenutz.storemngsim.data.datasource.sharedpref.Events
import com.tenutz.storemngsim.data.datasource.sharedpref.EventsSharedPref
import com.tenutz.storemngsim.data.datasource.sharedpref.Settings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        const val CHANNEL_ID = "high_importance_channel"
        const val CHANNEL_NAME = "store_management_sim"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        //token을 서버로 전송
        Logger.d("token: ${token}")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if(!Settings.pushReceived) return

        //수신한 메시지를 처리
        val messageBody: String = remoteMessage.data["body"].toString()
        val messageTitle: String = remoteMessage.data["title"].toString()
        val messageTopic: String = remoteMessage.data["topic"].toString().let {
            return@let if(it.contains("|")) it.substringBefore("|") else it
        }

        when(messageTopic) {
            "general" -> {
                EventsSharedPref.existsNewNotification = true
                Events.existsNewNotification.postValue(EventsSharedPref.existsNewNotification)
            }
        }

        val intent = Intent(baseContext, MainActivity::class.java)
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        intent.putExtra("existsNewNotification", true)
        val pendingIntent =
            PendingIntent.getActivity(baseContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        Logger.d("abcde")
//            PendingIntent.getActivity(baseContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val defaultSoundUri: Uri =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(baseContext, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setFullScreenIntent(pendingIntent, true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)

        CoroutineScope(Dispatchers.IO).launch {
            notificationBuilder
                .setLargeIcon(getLargeIcon())
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel =
                    NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
                notificationManager.createNotificationChannel(channel)
            }
            notificationManager.notify(0, notificationBuilder.build())
        }
    }

    private fun getLargeIcon(): Bitmap? {
        val drawable = getDrawable(R.mipmap.ic_launcher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && drawable is AdaptiveIconDrawable) {
            val backgroundDrawable = drawable.background  // 1
            val foregroundDrawable = drawable.foreground  // 2

            val drawables = arrayOfNulls<Drawable>(2)
            drawables[0] = backgroundDrawable
            drawables[1] = foregroundDrawable

            val layerDrawable = LayerDrawable(drawables)  // 3
            val width = layerDrawable.intrinsicWidth
            val height = layerDrawable.intrinsicHeight
            val bitmap = Bitmap.createBitmap(
                width, height, Bitmap.Config.ARGB_8888)  // 4
            val canvas = Canvas(bitmap)  // 5
            layerDrawable.setBounds(0, 0, canvas.width, canvas.height)
            layerDrawable.draw(canvas)  // 6
            return bitmap
        }
        return null
    }

}