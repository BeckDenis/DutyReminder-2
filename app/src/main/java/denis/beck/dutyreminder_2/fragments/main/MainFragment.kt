package denis.beck.dutyreminder_2.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import denis.beck.dutyreminder_2.R
import denis.beck.dutyreminder_2.databinding.FragmentMainBinding
import denis.beck.dutyreminder_2.epoxy.RemindController
import denis.beck.dutyreminder_2.fragments.newReminder.NewReminderFragment

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
        viewModel.getReminds()
        mainController = RemindController()
        binding.mainRv.setController(mainController)
        viewModel.data.observe(viewLifecycleOwner) {
            mainController.setData(it, true)
        }
        binding.newReminderButton.setOnClickListener {
            parentFragmentManager.commit {
                replace<NewReminderFragment>(R.id.fragment_container)
                addToBackStack(null)
            }
        }
    }
}