package denis.beck.reminder_list_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import denis.beck.features.reminderlistui.databinding.FragmentMainBinding
import denis.beck.navigation.Navigator
import denis.beck.navigation.NavigatorSingleton
import denis.beck.reminder_list_ui.epoxy.RemindController

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    // private var navigation = NavigatorSingleton.instance
    private val viewModel by viewModels<MainViewModel> { MainViewModel.Factory }
    private lateinit var mainController : RemindController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainController = RemindController()
        viewModel.setup()
        binding.setup()
    }

    private fun FragmentMainBinding.setup() {
        mainRv.setController(mainController)
        newReminderButton.setOnClickListener {
            openRemindFragment()
        }
    }

    private fun MainViewModel.setup() {
        data.observe(viewLifecycleOwner) {
            mainController.setData(it, true)
        }
        goToRemind.observe(viewLifecycleOwner, ::openRemindFragment)
    }

    private fun openRemindFragment(remindId: Long? = null) {
        // navigation.navigateToReminder(parentFragmentManager, remindId)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}