package denis.beck.dutyreminder_2.remindManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import denis.beck.dutyreminder_2.DutyReminderApp
import denis.beck.dutyreminder_2.notification.RemindNotificationManager
import denis.beck.dutyreminder_2.utils.goAsync
import timber.log.Timber
import java.lang.IllegalStateException

class RemindReceiver : BroadcastReceiver() {

    companion object {
        const val REMIND_ACTION = "remind.action"
        const val REMIND_ID_EXTRA = "remind.id.extra"
        const val REMIND_MESSAGE_EXTRA = "remind.message.extra"
    }

    override fun onReceive(context: Context?, intent: Intent?) = goAsync {
        if (intent?.action == REMIND_ACTION && context != null) {
            val id = intent.getLongExtra(REMIND_ID_EXTRA, -1)
            val message = intent.getStringExtra(REMIND_MESSAGE_EXTRA) ?: throw IllegalStateException("message is null")
            RemindNotificationManager(context).showNotification(id, message)
            val remindDao = (context.applicationContext as DutyReminderApp).remindDatabase.reminderDao()
            Timber.d("remind called, id:$id, message:$message prpr")
            remindDao.delete(id)
        }
    }
}