package denis.beck.dutyreminder_2.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import denis.beck.dutyreminder_2.databinding.FragmentReminderBinding
import denis.beck.dutyreminder_2.fragments.pickers.date.RemindDatePickerDialog
import denis.beck.dutyreminder_2.fragments.pickers.time.RemindTimePickerDialog
import denis.beck.dutyreminder_2.viewmodels.ReminderViewModel

class ReminderFragment :
    Fragment(),
    TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener {

    companion object {
        private const val REMIND_ID_ARG_KEY = "arg.remind.id"

        fun getInstance(remindId: Long?): ReminderFragment {
            val instance = ReminderFragment()
            instance.arguments = Bundle().apply {
                remindId?.let {
                    putLong(REMIND_ID_ARG_KEY, remindId)
                }
            }
            return instance
        }
    }

    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ReminderViewModel> { ReminderViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init(arguments?.getLong(REMIND_ID_ARG_KEY, -1))
        viewModel.setup()
        binding.setup()
    }

    private fun FragmentReminderBinding.setup() {
        timePickerButton.setOnClickListener {
            viewModel.onTimePickerButtonClick()
        }
        datePickerButton.setOnClickListener {
            viewModel.onDatePickerButtonClick()
        }
        saveButton.setOnClickListener {
            viewModel.onSaveButtonClick(
                viewModel.timestamp,
                binding.messageTextField.text.toString(),
                binding.weekView.selectedDayOfWeeks
            )
        }
        deleteButton.isVisible = viewModel.state == RemindViewState.CHANGE
        deleteButton.setOnClickListener {
            viewModel.onDeleteButtonClick()
        }
        weekView.setOnWeekDaySelectedListener { selectedWeekDays ->
            viewModel.onWeekDaySelected(selectedWeekDays)
        }
    }

    private fun ReminderViewModel.setup() {
        showDatePicker.observe(viewLifecycleOwner) {
            RemindDatePickerDialog.show(childFragmentManager)
        }
        showTimePicker.observe(viewLifecycleOwner) {
            RemindTimePickerDialog.show(childFragmentManager)
        }
        goBack.observe(viewLifecycleOwner) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        pickedDateText.observe(viewLifecycleOwner) { date ->
            binding.pickedDateText.text = date
        }
        pickedTimeText.observe(viewLifecycleOwner) { time ->
            binding.pickedTimeText.text = time
        }
        message.observe(viewLifecycleOwner) { message ->
            binding.messageTextField.setText(message)
        }
        dateTextVisibility.observe(viewLifecycleOwner) { isVisible ->
            binding.dateContainer.isVisible = isVisible
        }
        setSelectedDaysOfWeek.observe(viewLifecycleOwner) { selectedWeekDays ->
            binding.weekView.selectedDayOfWeeks = selectedWeekDays
        }
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        viewModel.setTime(hourOfDay, minute)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        viewModel.setDate(year, month, day)
    }
}