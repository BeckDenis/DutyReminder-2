package denis.beck.dutyreminder_2.reboot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_REBOOT
import denis.beck.common_core.extensions.goAsync
import denis.beck.dutyreminder_2.DutyReminderApp
import denis.beck.reminder.domain.remindManager.RemindManager
import javax.inject.Inject

class RebootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var reminderManager: RemindManager

    override fun onReceive(context: Context?, intent: Intent?) = goAsync {
        if (intent?.action == ACTION_REBOOT && context != null) {
            (context.applicationContext as DutyReminderApp).applicationGraph.inject(this@RebootReceiver)
            reminderManager.handleReboot()
        }
    }
}