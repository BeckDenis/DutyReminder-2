package denis.beck.navigation

import android.os.Bundle

sealed class Screen(bundle: Bundle) {
    class Reminder(bundle: Bundle): Screen(bundle)
    class ReminderList(bundle: Bundle): Screen(bundle)
}