package denis.beck.dutyreminder_2.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import denis.beck.navigation.Navigator
import denis.beck.reminder_ui.ReminderFragment

class NavigatorImpl : Navigator {

    override fun navigateToReminder(fragmentManager: FragmentManager, remindId: Long?) {
        val fragment = ReminderFragment.getInstance(remindId)
        fragmentManager.navigateTo(fragment)
    }

    override fun navigateBack(activity: AppCompatActivity) {
        activity.onBackPressedDispatcher.onBackPressed()
    }

    private fun FragmentManager.navigateTo(fragment: Fragment) {
        this.commit {
            replace(denis.beck.dutyreminder_2.R.id.fragment_container, fragment)
            addToBackStack(null)
        }
    }
}