package denis.beck.dutyreminder_2.fragments.newReminder

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import denis.beck.dutyreminder_2.databinding.FragmentNewReminderBinding
import denis.beck.dutyreminder_2.fragments.pickers.common.PickersViewModel
import denis.beck.dutyreminder_2.fragments.pickers.date.RemindDatePickerDialog
import denis.beck.dutyreminder_2.fragments.pickers.time.RemindTimePickerDialog

class NewReminderFragment :
    Fragment(),
    TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener {

    private var _binding: FragmentNewReminderBinding? = null
    private val binding get() = _binding!!

    private val pickersViewModel by viewModels<PickersViewModel>()
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
        pickersViewModel.setup()
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
                pickersViewModel.timestamp,
                binding.messageTextField.text.toString()
            )
        }
    }

    private fun NewReminderViewModel.setup() {
        showDatePicker.observe(viewLifecycleOwner) {
            RemindDatePickerDialog().show(childFragmentManager, "datePicker")
        }
        showTimePicker.observe(viewLifecycleOwner) {
            RemindTimePickerDialog().show(childFragmentManager, "timePicker")
        }
        goBack.observe(viewLifecycleOwner) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun PickersViewModel.setup() {
        pickedDateAndTimeText.observe(viewLifecycleOwner) { text ->
            binding.pickedTimeAndDateText.text = text
        }
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        pickersViewModel.setTime(hourOfDay, minute)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        pickersViewModel.setDate(year, month, day)
    }
}