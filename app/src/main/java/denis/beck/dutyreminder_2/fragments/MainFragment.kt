package denis.beck.dutyreminder_2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import denis.beck.dutyreminder_2.R
import denis.beck.dutyreminder_2.databinding.FragmentMainBinding
import denis.beck.dutyreminder_2.epoxy.RemindController
import denis.beck.dutyreminder_2.viewmodels.MainViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

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
        parentFragmentManager.commit {
            replace(R.id.fragment_container, ReminderFragment.getInstance(remindId))
            addToBackStack(null)
        }
    }
}