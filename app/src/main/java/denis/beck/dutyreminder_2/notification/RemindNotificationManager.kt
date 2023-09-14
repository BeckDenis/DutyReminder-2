package denis.beck.dutyreminder_2.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import denis.beck.dutyreminder_2.R

class RemindNotificationManager(private val context: Context) {
    companion object {
        private const val CHANNEL_ID = "remind.channel"
    }

    fun showNotification(id: Long, message: String) {
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = createNotificationChannel()
        val notification = createNotification(message)

        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(id.toInt(), notification)
    }

    private fun createNotification(message: String) = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_pin)
        .setContentTitle("Duty Reminder")
        .setContentText(message)
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