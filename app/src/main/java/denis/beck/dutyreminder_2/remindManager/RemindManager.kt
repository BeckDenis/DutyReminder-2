package denis.beck.dutyreminder_2.remindManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import denis.beck.dutyreminder_2.models.Remind
import timber.log.Timber

class RemindManager(private val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setReminder(remind: Remind) {
        val intent = Intent(context, RemindReceiver::class.java).apply {
            action = RemindReceiver.REMIND_ACTION
            putExtra(RemindReceiver.REMIND_ID_EXTRA, remind.id)
            putExtra(RemindReceiver.REMIND_MESSAGE_EXTRA, remind.message)
        }

        val pendingIntent = PendingIntent
            .getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        Timber.d("remind set in ${System.currentTimeMillis()} on ${remind.timestamp} prpr")
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, remind.timestamp, pendingIntent)
    }
}