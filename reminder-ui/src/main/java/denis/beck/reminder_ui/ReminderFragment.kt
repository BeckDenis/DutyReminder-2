package denis.beck.reminder_ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import denis.beck.common.viewModel.ViewModelFactory
import denis.beck.navigation.Navigator
import denis.beck.pickers.pickers.date.RemindDatePickerDialog
import denis.beck.pickers.pickers.time.RemindTimePickerDialog
import denis.beck.reminder_ui.databinding.FragmentReminderBinding
import denis.beck.reminder_ui.di.DaggerReminderComponent
import denis.beck.reminder_ui.di.ReminderDependenciesProvider
import javax.inject.Inject

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

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ReminderViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var navigator: Navigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val dependencies = (requireActivity() as ReminderDependenciesProvider).reminderDependencies()
        val remindId = arguments?.getLong(REMIND_ID_ARG_KEY, -1)
        val component = DaggerReminderComponent.factory().create(requireContext(), remindId, dependencies)
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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