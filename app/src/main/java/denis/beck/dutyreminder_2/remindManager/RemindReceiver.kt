package denis.beck.dutyreminder_2.remindManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import denis.beck.dutyreminder_2.utils.showToast
import timber.log.Timber

class RemindReceiver : BroadcastReceiver() {

    companion object {
        const val REMIND_ACTION = "remind.action"
        const val REMIND_EXTRA = "remind.extra"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.d("intent received prpr")
        if (intent?.action == REMIND_ACTION) {
            val extra = intent.getStringExtra(REMIND_EXTRA)
            context?.showToast("Remind Action Received with extra: $extra")
        }
    }
}