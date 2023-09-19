package denis.beck.dutyreminder_2.remindManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import denis.beck.dutyreminder_2.epoxy.repositories.RemindRepository
import denis.beck.dutyreminder_2.models.RemindDomainModel
import denis.beck.dutyreminder_2.utils.toDateAndTimeLogString
import denis.beck.dutyreminder_2.utils.toDateAndTimeString
import timber.log.Timber
import java.util.Calendar
import java.util.Date

class RemindManager(
    private val context: Context,
    private val remindRepository: RemindRepository,
) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    suspend fun setReminder(remind: RemindDomainModel) {
        val id = remindRepository.setRemind(remind)

        val pendingIntent = getPendingIntent(remind.copy(id = id))

        logNewReminder(remind)
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, remind.timestamp, pendingIntent)
    }

    private fun logNewReminder(remind: RemindDomainModel) {
        val date = Calendar.getInstance().apply {
            time = Date(remind.timestamp)
        }
        val currentDate = Calendar.getInstance().apply {
            Date(System.currentTimeMillis())
        }
        Timber.d("remind set in ${currentDate.toDateAndTimeLogString()} on ${date.toDateAndTimeLogString()} with id:${remind.id} and message: ${remind.message} prpr")
    }

    suspend fun updateReminder(newRemind: RemindDomainModel, oldRemind: RemindDomainModel) {
        // Удалить. обновляется по request коду
        val oldPendingIntent = getPendingIntent(oldRemind)
        alarmManager.cancel(oldPendingIntent)

        val newPendingIntent = getPendingIntent(newRemind)
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, newRemind.timestamp, newPendingIntent)
        remindRepository.updateRemind(newRemind)

        logNewReminder(newRemind)
    }

    suspend fun deleteReminder(remind: RemindDomainModel) {
        val pendingIntent = getPendingIntent(remind)
        alarmManager.cancel(pendingIntent)
        remindRepository.deleteRemind(remind.id)
    }

    private fun getPendingIntent(remindDomainModel: RemindDomainModel): PendingIntent {
        val intent = Intent(context, RemindReceiver::class.java).apply {
            action = RemindReceiver.REMIND_ACTION
            putExtra(RemindReceiver.REMIND_ID_EXTRA, remindDomainModel.id)
            putExtra(RemindReceiver.REMIND_MESSAGE_EXTRA, remindDomainModel.message)
        }

        return PendingIntent
            .getBroadcast(context, remindDomainModel.id.toInt(), intent, PendingIntent. FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
    }
}