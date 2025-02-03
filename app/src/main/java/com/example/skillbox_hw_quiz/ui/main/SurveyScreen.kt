package com.example.skillbox_hw_quiz.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.databinding.FragmentSurveyScreenBinding
import com.example.skillbox_hw_quiz.quiz.QuizStorage
import java.util.Locale

const val ARG_PARAM1 = "resultForSend"

class SurveyScreen : Fragment() {
    private var _binding: FragmentSurveyScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSurveyScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val finalListOfAnswers = mutableListOf<Int>()

        var firstAnswer = 10
        var secondAnswer = 10
        var thirdAnswer = 10

        var firstAnswerCheck = false
        var secondAnswerCheck = false
        var thirdAnswerCheck = false

        val firstQuestionBlock = binding.firstQuestionBlock
        val secondQuestionBlock = binding.secondQuestionBlock
        val thirdQuestionBlock = binding.thirdQuestionBlock

        fun sendButtonStatus() {
            if (firstAnswerCheck && secondAnswerCheck && thirdAnswerCheck) {
                binding.sendAnswerButton.isEnabled = true
            }
        }

        firstQuestionBlock.setQuizText(0)
        secondQuestionBlock.setQuizText(1)
        thirdQuestionBlock.setQuizText(2)

        fun animatedQuestionGroup(questionGroup: RadioGroup) {
            questionGroup.animate().apply {
                duration = 3000
                alpha(1f)
            }
        }

        val firstQuestionGroup = firstQuestionBlock.questionGroupRequest()
        val secondQuestionGroup = secondQuestionBlock.questionGroupRequest()
        val thirdQuestionGroup = thirdQuestionBlock.questionGroupRequest()

        animatedQuestionGroup(firstQuestionGroup)
        animatedQuestionGroup(secondQuestionGroup)
        animatedQuestionGroup(thirdQuestionGroup)

        firstQuestionGroup.setOnCheckedChangeListener { _, _ ->
            firstAnswer = firstQuestionBlock.answerCheck(0)
            firstAnswerCheck = true
            sendButtonStatus()
        }

        secondQuestionGroup.setOnCheckedChangeListener { _, _ ->
            secondAnswer = secondQuestionBlock.answerCheck(1)
            secondAnswerCheck = true
            sendButtonStatus()
        }

        thirdQuestionGroup.setOnCheckedChangeListener { _, _ ->
            thirdAnswer = thirdQuestionBlock.answerCheck(2)
            thirdAnswerCheck = true
            sendButtonStatus()
        }

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_surveyScreen_to_mainFragment)
        }

        binding.sendAnswerButton.setOnClickListener {
            val quiz = QuizCustomView(
                requireContext(),
                null
            ).getQuizLocale(Locale.getDefault().displayLanguage)
            finalListOfAnswers.add(0, firstAnswer)
            finalListOfAnswers.add(1, secondAnswer)
            finalListOfAnswers.add(2, thirdAnswer)
            val listOfAnswersToSend = QuizStorage.answer(
                quiz,
                answers = finalListOfAnswers
            )
            val bundle = bundleOf(ARG_PARAM1 to listOfAnswersToSend)
            findNavController().navigate(
                R.id.action_surveyScreen_to_resultsScreen, bundle
            )
        }
    }
}