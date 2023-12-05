package denis.beck.dutyreminder_2.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import denis.beck.dutyreminder_2.R
import denis.beck.login_ui.presentation.LoginFragment
import denis.beck.navigation.Navigator
import denis.beck.reminder_list_ui.ReminderListFragment
import denis.beck.reminder_ui.ReminderFragment
import javax.inject.Inject

class NavigatorImpl @Inject constructor(): Navigator {
    override fun navigateToLogin(fragmentManager: FragmentManager) {
        fragmentManager.clear()
        fragmentManager.commit {
            add(R.id.fragment_container, LoginFragment.newInstance())
        }
    }

    override fun navigateToMain(fragmentManager: FragmentManager) {
        fragmentManager.clear()
        fragmentManager.commit {
            add(R.id.fragment_container, ReminderListFragment.newInstance())
        }
    }

    override fun navigateToReminder(fragmentManager: FragmentManager, remindId: Long?) {
        val fragment = ReminderFragment.getInstance(remindId)
        fragmentManager.navigateTo(fragment)
    }

    override fun navigateBack(activity: AppCompatActivity) {
        activity.onBackPressedDispatcher.onBackPressed()
    }

    private fun FragmentManager.navigateTo(fragment: Fragment) {
        this.commit {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }
    }

    private fun FragmentManager.clear() {
        for (fragment in this.fragments) {
            this.beginTransaction().remove(fragment).commit()
        }
    }
}