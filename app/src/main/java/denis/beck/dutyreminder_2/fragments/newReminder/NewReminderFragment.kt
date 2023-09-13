package denis.beck.dutyreminder_2.fragments.newReminder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import denis.beck.dutyreminder_2.R
import denis.beck.dutyreminder_2.databinding.FragmentMainBinding
import denis.beck.dutyreminder_2.databinding.FragmentNewReminderBinding
import denis.beck.dutyreminder_2.fragments.pickers.common.PickersCommonViewModel
import denis.beck.dutyreminder_2.fragments.pickers.date.DatePickerDialog
import denis.beck.dutyreminder_2.fragments.pickers.time.TimePickerDialog
import denis.beck.dutyreminder_2.remindManager.RemindManager

class NewReminderFragment : Fragment() {

    private var _binding: FragmentNewReminderBinding? = null
    private val binding get() = _binding!!

    private val pickersSharedViewModel by activityViewModels<PickersCommonViewModel>()
    private val viewModel by viewModels<NewReminderViewModel> { NewReminderViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pickersSharedViewModel.setup()
        viewModel.setup()
        binding.setup()
    }

    private fun FragmentNewReminderBinding.setup() {
        timePickerButton.setOnClickListener {
            viewModel.onTimePickerButtonClick()
        }
        datePickerButton.setOnClickListener {
            viewModel.onDatePickerButtonClick()
        }
        saveButton.setOnClickListener {
            viewModel.onSaveButtonClicked(
                pickersSharedViewModel.timestamp,
                binding.messageTextField.text.toString()
            )
        }
    }

    private fun NewReminderViewModel.setup() {
        showDatePicker.observe(viewLifecycleOwner) {
            DatePickerDialog().show(childFragmentManager, "datePicker")
        }
        showTimePicker.observe(viewLifecycleOwner) {
            TimePickerDialog().show(childFragmentManager, "timePicker")
        }
        goBack.observe(viewLifecycleOwner) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun PickersCommonViewModel.setup() {
        pickedDateAndTimeText.observe(viewLifecycleOwner) { text ->
            binding.pickedTimeAndDateText.text = text
        }
    }
}