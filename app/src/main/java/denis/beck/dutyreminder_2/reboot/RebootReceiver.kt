package denis.beck.dutyreminder_2.reboot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_REBOOT
import denis.beck.dutyreminder_2.DutyReminderApp
import denis.beck.dutyreminder_2.utils.goAsync

class RebootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) = goAsync {
        if (intent?.action == ACTION_REBOOT) {
            val remindDao = (context?.applicationContext as DutyReminderApp).remindDatabase.reminderDao()
            val expiredReminds = remindDao.getAll().filter { it.timestamp < System.currentTimeMillis() }
            expiredReminds.forEach { remindEntry ->
                remindDao.delete(remindEntry)
            }
        }
    }
}