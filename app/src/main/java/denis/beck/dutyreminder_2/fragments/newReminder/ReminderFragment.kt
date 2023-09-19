package denis.beck.dutyreminder_2.fragments.newReminder

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
import denis.beck.dutyreminder_2.databinding.FragmentNewReminderBinding
import denis.beck.dutyreminder_2.fragments.pickers.date.RemindDatePickerDialog
import denis.beck.dutyreminder_2.fragments.pickers.time.RemindTimePickerDialog

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

    private var _binding: FragmentNewReminderBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ReminderViewModel> { ReminderViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init(arguments?.getLong(REMIND_ID_ARG_KEY, -1))
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
            viewModel.onSaveButtonClick(
                viewModel.timestamp,
                binding.messageTextField.text.toString()
            )
        }
        deleteButton.isVisible = viewModel.state == RemindViewState.CHANGE
        deleteButton.setOnClickListener {
            viewModel.onDeleteButtonClick()
        }
    }

    private fun ReminderViewModel.setup() {
        showDatePicker.observe(viewLifecycleOwner) {
            RemindDatePickerDialog().show(childFragmentManager, "datePicker")
        }
        showTimePicker.observe(viewLifecycleOwner) {
            RemindTimePickerDialog().show(childFragmentManager, "timePicker")
        }
        goBack.observe(viewLifecycleOwner) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        pickedDateAndTimeText.observe(viewLifecycleOwner) { dateAndTime ->
            binding.pickedTimeAndDateText.text = dateAndTime
        }
        message.observe(viewLifecycleOwner) { message ->
            binding.messageTextField.setText(message)
        }
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        viewModel.setTime(hourOfDay, minute)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        viewModel.setDate(year, month, day)
    }
}