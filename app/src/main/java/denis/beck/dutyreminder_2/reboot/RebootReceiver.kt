package denis.beck.dutyreminder_2.reboot

import RemindDomainModel
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_REBOOT
import denis.beck.common.extensions.goAsync
import denis.beck.dutyreminder_2.DutyReminderApp

class RebootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) = goAsync {
        if (intent?.action == ACTION_REBOOT && context != null) {
            val remindDao = (context.applicationContext as DutyReminderApp).applicationGraph.remindDao()
            val remindRepository = denis.beck.reminder.RemindRepository(remindDao)
            val reminds = remindRepository.getReminds()
            val currentTime = System.currentTimeMillis()

            deleteExpiredReminds(reminds, currentTime, remindRepository)
            resetActualReminds(context, reminds, currentTime, remindRepository)
        }
    }

    private suspend fun resetActualReminds(
        context: Context,
        reminds: List<RemindDomainModel>,
        currentTime: Long,
        remindRepository: denis.beck.reminder.RemindRepository,
    ) {
        val remindManager = denis.beck.reminder.domain.remindManager.RemindManager(context, remindRepository)
        val actualReminds = reminds.filter { it.timestamp >= (currentTime + 1000L) }
        actualReminds.forEach {
            remindManager.setReminder(it)
        }
    }

    private suspend fun deleteExpiredReminds(
        reminds: List<RemindDomainModel>,
        currentTime: Long,
        remindRepository: denis.beck.reminder.RemindRepository
    ) {
        val expiredReminds = reminds.filter { it.timestamp < (currentTime + 1000L) }
        expiredReminds.forEach { remind ->
            remindRepository.deleteRemind(remind.id)
        }
    }
}