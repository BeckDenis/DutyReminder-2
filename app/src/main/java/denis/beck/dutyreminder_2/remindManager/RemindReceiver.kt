package denis.beck.dutyreminder_2.remindManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import denis.beck.dutyreminder_2.DutyReminderApp
import denis.beck.dutyreminder_2.utils.goAsync
import denis.beck.dutyreminder_2.utils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class RemindReceiver : BroadcastReceiver() {

    companion object {
        const val REMIND_ACTION = "remind.action"
        const val REMIND_ID_EXTRA = "remind.id.extra"
        const val REMIND_MESSAGE_EXTRA = "remind.message.extra"
    }

    override fun onReceive(context: Context?, intent: Intent?) = goAsync {
        if (intent?.action == REMIND_ACTION) {
            val id = intent.getLongExtra(REMIND_ID_EXTRA, -1)
            val message = intent.getStringExtra(REMIND_MESSAGE_EXTRA)
            withContext(Dispatchers.Main) {
                context?.showToast("Remind Action Received with extra: $message")
            }
            val remindDao = (context?.applicationContext as DutyReminderApp).remindDatabase.reminderDao()
            Timber.d("deleted id: $id prpr")
            remindDao.delete(id)
        }
    }
}