package denis.beck.dutyreminder_2.reboot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_REBOOT
import denis.beck.dutyreminder_2.DutyReminderApp
import denis.beck.dutyreminder_2.remindManager.RemindManager
import denis.beck.dutyreminder_2.room.RemindDao
import denis.beck.dutyreminder_2.room.RemindEntity
import denis.beck.dutyreminder_2.utils.goAsync

class RebootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) = goAsync {
        if (intent?.action == ACTION_REBOOT && context != null) {
            val remindDao = (context.applicationContext as DutyReminderApp).remindDatabase.reminderDao()
            val reminds = remindDao.getAll()
            val currentTime = System.currentTimeMillis()

            deleteExpiredReminds(reminds, currentTime, remindDao)
            resetActualReminds(context, reminds, currentTime)
        }
    }

    private fun resetActualReminds(
        context: Context,
        reminds: List<RemindEntity>,
        currentTime: Long
    ) {
        val remindManager = RemindManager(context)
        val actualReminds = reminds.filter { it.timestamp >= (currentTime + 1000L) }
        actualReminds.forEach {
            remindManager.setReminder(it.toRemind())
        }
    }

    private fun deleteExpiredReminds(
        reminds: List<RemindEntity>,
        currentTime: Long,
        remindDao: RemindDao
    ) {
        val expiredReminds = reminds.filter { it.timestamp < (currentTime + 1000L) }
        expiredReminds.forEach { remindEntry ->
            remindDao.delete(remindEntry)
        }
    }
}