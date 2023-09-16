package denis.beck.dutyreminder_2.remindManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import denis.beck.dutyreminder_2.epoxy.repositories.RemindRepository
import denis.beck.dutyreminder_2.models.RemindDomainModel
import timber.log.Timber

class RemindManager(
    private val context: Context,
    private val remindRepository: RemindRepository,
) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    suspend fun setReminder(remind: RemindDomainModel) {
        val id = remindRepository.setRemind(remind)

        val pendingIntent = getPendingIntent(remind.copy(id = id))

        Timber.d("remind set in ${System.currentTimeMillis()} on ${remind.timestamp} prpr")
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, remind.timestamp, pendingIntent)
    }

    private fun getPendingIntent(remindDomainModel: RemindDomainModel): PendingIntent {
        val intent = Intent(context, RemindReceiver::class.java).apply {
            action = RemindReceiver.REMIND_ACTION
            putExtra(RemindReceiver.REMIND_ID_EXTRA, remindDomainModel.id)
            putExtra(RemindReceiver.REMIND_MESSAGE_EXTRA, remindDomainModel.message)
        }

        return PendingIntent
            .getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    suspend fun updateReminder(newRemind: RemindDomainModel, oldRemind: RemindDomainModel) {
        val oldPendingIntent = getPendingIntent(oldRemind)
        alarmManager.cancel(oldPendingIntent)

        val newPendingIntent = getPendingIntent(newRemind)
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, newRemind.timestamp, newPendingIntent)

        remindRepository.updateRemind(newRemind)
    }

    suspend fun deleteReminder(remind: RemindDomainModel) {
        val pendingIntent = getPendingIntent(remind)
        alarmManager.cancel(pendingIntent)
        remindRepository.deleteRemind(remind.id)
    }
}