package denis.beck.dutyreminder_2.remindManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import timber.log.Timber

class RemindManager(private val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setReminder() {
        val intent = Intent(context, RemindReceiver::class.java).apply {
            action = RemindReceiver.REMIND_ACTION
            putExtra(RemindReceiver.REMIND_EXTRA, "test remind extra")
        }

        val pendingIntent = PendingIntent
            .getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val alarmDelayInSeconds = 10
        val alarmTimeUTC = System.currentTimeMillis() + alarmDelayInSeconds * 1_000L
        Timber.d("remind set in ${System.currentTimeMillis()} prpr")
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTimeUTC, pendingIntent)

    }
}