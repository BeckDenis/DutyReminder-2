package denis.beck.dutyreminder_2.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import denis.beck.dutyreminder_2.DutyReminderApp
import denis.beck.dutyreminder_2.epoxy.repositories.RemindRepository
import denis.beck.dutyreminder_2.managers.RemindManager
import denis.beck.dutyreminder_2.managers.RemindNotificationManager
import denis.beck.dutyreminder_2.utils.goAsync
import timber.log.Timber
import java.lang.IllegalStateException

class RemindReceiver : BroadcastReceiver() {

    companion object {
        const val REMIND_ACTION = "remind.action"
        const val REMIND_ID_EXTRA = "remind.id.extra"
        const val REMIND_MESSAGE_EXTRA = "remind.message.extra"
        const val REMIND_IS_PERIODICAL = "remind.periodical.extra"
    }

    override fun onReceive(context: Context?, intent: Intent?) = goAsync {
        if (intent?.action == REMIND_ACTION && context != null) {
            val id = intent.getLongExtra(REMIND_ID_EXTRA, -1)
            val message = intent.getStringExtra(REMIND_MESSAGE_EXTRA) ?: throw IllegalStateException("message is null")
            RemindNotificationManager(context).showNotification(id, message)

            Timber.d("remind called, id:$id, message:$message prpr")

            val isPeriodical = intent.getBooleanExtra(REMIND_IS_PERIODICAL, false)
            if (isPeriodical) {
                val remindDao = (context.applicationContext as DutyReminderApp).remindDatabase.reminderDao()
                val remindManager = RemindManager(context, RemindRepository(remindDao))
                remindDao.get(id)?.toDomain()?.let { remind ->
                    remindManager.restartReminder(remind)
                }
            }
        }
    }
}