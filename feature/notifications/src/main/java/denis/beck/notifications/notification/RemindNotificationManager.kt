package denis.beck.notifications.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import denis.beck.notifications.R
import javax.inject.Inject

class RemindNotificationManager @Inject constructor(private val context: Context) {
    companion object {
        private const val CHANNEL_ID = "remind.channel"
    }

    fun showNotification(id: Long, message: String, description: String) {
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = createNotificationChannel()
        val notification = createNotification(message, description)

        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(id.toInt(), notification)
    }

    private fun createNotification(message: String, description: String) = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_pin)
        .setContentTitle(message)
        .setContentText(description)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

    private fun createNotificationChannel() : NotificationChannel {
        val name = context.getString(R.string.channel_name)
        val descriptionText = context.getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        return NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
    }
}