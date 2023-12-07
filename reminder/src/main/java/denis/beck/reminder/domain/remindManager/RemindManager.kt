package denis.beck.reminder.domain.remindManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import denis.beck.common.extensions.toDateAndTimeLogString
import denis.beck.reminder.RemindReceiver
import denis.beck.reminder.data.RemindRepository
import timber.log.Timber
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class RemindManager @Inject constructor(
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

    suspend fun restartReminder(id: Long) {
        val remind = remindRepository
            .getRemind(id)
            ?.let(RemindDomainModel::fromEntity) ?: return

        val pendingIntent = getPendingIntent(remind)

        logNewReminder(remind)
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, remind.getNearestTimestamp(), pendingIntent)
    }

    suspend fun updateReminder(newRemind: RemindDomainModel) {
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
            putExtra(RemindReceiver.REMIND_IS_PERIODICAL, remindDomainModel.selectedDaysOfWeek.isNotEmpty())
        }

        return PendingIntent
            .getBroadcast(context, remindDomainModel.id.toInt(), intent, PendingIntent. FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
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

    /**
     * Обрабатываем старт устройства. Удаляем просроченные уведомления и устанавливаем будущие,
     * так как после выключения устройства все задачи сбрасываются
     */
    suspend fun handleReboot() {
        val reminds = remindRepository
            .getReminds()
            .map(RemindDomainModel::fromEntity)

        val currentTime = System.currentTimeMillis()

        deleteExpiredReminds(reminds, currentTime)
        resetActualReminds(reminds, currentTime)
    }

    private suspend fun resetActualReminds(
        reminds: List<RemindDomainModel>,
        currentTime: Long,
    ) {
        val actualReminds = reminds.filter { it.timestamp >= (currentTime + 1000L) }
        actualReminds.forEach {
            setReminder(it)
        }
    }

    private suspend fun deleteExpiredReminds(
        reminds: List<RemindDomainModel>,
        currentTime: Long
    ) {
        val expiredReminds = reminds.filter { it.timestamp < (currentTime + 1000L) }
        expiredReminds.forEach { remind ->
            remindRepository.deleteRemind(remind.id)
        }
    }
}