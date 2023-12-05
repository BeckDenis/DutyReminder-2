package denis.beck.reminder_list_ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import denis.beck.common.viewModel.ViewModelFactory
import denis.beck.features.reminderlistui.databinding.FragmentMainBinding
import denis.beck.navigation.Navigator
import denis.beck.reminder_list_ui.di.DaggerReminderListComponent
import denis.beck.reminder_list_ui.di.ReminderListDependenciesProvider
import denis.beck.reminder_list_ui.epoxy.RemindController
import javax.inject.Inject

class ReminderListFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ReminderListViewModel by viewModels { viewModelFactory }

    @Inject
    internal lateinit var mainController: RemindController

    @Inject
    lateinit var navigator: Navigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val dependencies = (requireActivity() as ReminderListDependenciesProvider).reminderListDependencies()
        val component = DaggerReminderListComponent.factory().create(requireContext(), dependencies)
        component.inject(this)
    }

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
        exitButton.setOnClickListener {
            viewModel.onLogout()
        }
    }

    private fun ReminderListViewModel.setup() {
        data.observe(viewLifecycleOwner) {
            mainController.setData(it, true)
        }
        goToRemind.observe(viewLifecycleOwner, ::openRemindFragment)
        goToLogin.observe(viewLifecycleOwner) { openLoginFragment() }
    }

    private fun openLoginFragment() {
        navigator.navigateToLogin(parentFragmentManager)
    }

    private fun openRemindFragment(remindId: Long? = null) {
        navigator.navigateToReminder(parentFragmentManager, remindId)
    }

    companion object {
        fun newInstance() = ReminderListFragment()
    }
}