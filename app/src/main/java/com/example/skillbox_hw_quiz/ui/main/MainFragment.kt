package com.example.skillbox_hw_quiz.ui.main

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.databinding.MainFragmentBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.util.Locale

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    val calendar = Calendar.getInstance()

    private val dateFormatRu = DateFormat.getDateInstance(DateFormat.SHORT, Locale("ru"))
    private val dateFormatEn = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ENGLISH)

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainButtonStart.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_surveyScreen)
        }

        binding.dateButton.setOnClickListener {
            val calendarConstraints =
                CalendarConstraints.Builder().setOpenAt(calendar.timeInMillis).build()
            val birthdayDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(resources.getString(R.string.date_tittle_text_main_screen))
                .setCalendarConstraints(calendarConstraints)
                .build()
            birthdayDatePicker.addOnPositiveButtonClickListener { timeInMillis ->
                calendar.timeInMillis = timeInMillis
                val formattedDate: String
                if (Locale.getDefault().displayLanguage == "русский") {
                    formattedDate = dateFormatRu.format(calendar.time)
                } else {
                    formattedDate = dateFormatEn.format(calendar.time)
                }
                Snackbar.make(
                    binding.dateButton,
                    formattedDate,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            birthdayDatePicker.show(parentFragmentManager, "birthdayDatePicker")
        }
    }
}