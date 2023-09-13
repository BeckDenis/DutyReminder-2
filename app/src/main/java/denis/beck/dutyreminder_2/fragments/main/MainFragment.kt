package denis.beck.dutyreminder_2.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import denis.beck.dutyreminder_2.R
import denis.beck.dutyreminder_2.databinding.FragmentMainBinding
import denis.beck.dutyreminder_2.fragments.newReminder.NewReminderFragment
import denis.beck.dutyreminder_2.fragments.pickers.common.PickersCommonViewModel
import denis.beck.dutyreminder_2.fragments.pickers.date.DatePickerDialog
import denis.beck.dutyreminder_2.fragments.pickers.time.TimePickerDialog
import denis.beck.dutyreminder_2.remindManager.RemindManager

class MainFragment() : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newReminderButton.setOnClickListener {
            parentFragmentManager.commit {
                replace<NewReminderFragment>(R.id.fragment_container)
                addToBackStack(null)
            }
        }
    }
}