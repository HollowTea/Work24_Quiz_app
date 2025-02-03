package com.example.skillbox_hw_quiz.ui.main

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RadioGroup
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.databinding.QuizBlocksBinding
import com.example.skillbox_hw_quiz.quiz.Quiz
import com.example.skillbox_hw_quiz.quiz.QuizStorage
import java.util.Locale

class QuizCustomView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {

    private val binding: QuizBlocksBinding

    init {
        val inflatedView = inflate(context, R.layout.quiz_blocks, this)
        binding = QuizBlocksBinding.bind(inflatedView)
    }

    fun getQuizLocale(localeLanguage: String): Quiz {
        val currentQuiz: Quiz = if (Locale.getDefault().displayLanguage == "русский") {
            QuizStorage.getQuiz(QuizStorage.Locale.Ru)
        } else {
            QuizStorage.getQuiz(QuizStorage.Locale.En)
        }
        return currentQuiz
    }

    fun questionGroupRequest(): RadioGroup {
        return binding.questionGroup
    }

    fun setQuizText(questionNumber: Int) {
        val quiz = getQuizLocale(Locale.getDefault().displayLanguage)
        binding.question.text = quiz.questions[questionNumber].question
        binding.questionAnswer1.text = quiz.questions[questionNumber].answers[0]
        binding.questionAnswer2.text = quiz.questions[questionNumber].answers[1]
        binding.questionAnswer3.text = quiz.questions[questionNumber].answers[2]
        binding.questionAnswer4.text = quiz.questions[questionNumber].answers[3]
    }

    fun answerCheck(questionGroup: Int): Int {
        var answer = 10
        when (binding.questionGroup.checkedRadioButtonId) {
            binding.questionAnswer1.id -> answer = 0
            binding.questionAnswer2.id -> answer = 1
            binding.questionAnswer3.id -> answer = 2
            binding.questionAnswer4.id -> answer = 3
        }
        return answer
    }
}