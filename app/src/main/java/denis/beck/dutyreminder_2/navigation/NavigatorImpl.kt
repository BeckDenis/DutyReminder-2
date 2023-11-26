package denis.beck.dutyreminder_2.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import dagger.Binds
import dagger.Module
import denis.beck.dutyreminder_2.R
import denis.beck.login_ui.presentation.LoginFragment
import denis.beck.navigation.Navigator
import denis.beck.reminder_list_ui.MainFragment
import denis.beck.reminder_ui.ReminderFragment
import javax.inject.Inject
import javax.inject.Singleton

class NavigatorImpl @Inject constructor(): Navigator {
    override fun navigateToLogin(fragmentManager: FragmentManager) {
        fragmentManager.commit {
            add(R.id.fragment_container, LoginFragment.newInstance())
        }
    }

    override fun navigateToMain(fragmentManager: FragmentManager) {
        fragmentManager.commit {
            replace(R.id.fragment_container, MainFragment.newInstance())
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
        }
    }
}