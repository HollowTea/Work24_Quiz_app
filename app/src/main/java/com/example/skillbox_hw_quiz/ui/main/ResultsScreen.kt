package com.example.skillbox_hw_quiz.ui.main

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationSet
import androidx.navigation.fragment.findNavController
import com.example.skillbox_hw_quiz.MainActivity
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.databinding.FragmentResultsScreenBinding

class ResultsScreen : Fragment() {

    private var _binding: FragmentResultsScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultsScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ObjectAnimator.ofArgb(
            binding.thanksMessage,
            "textColor",
            Color.parseColor("#000000"),
            Color.parseColor("#FF03DAC5")
        ).apply {
            duration = 2000
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = 3
            repeatMode = ObjectAnimator.REVERSE
            start()
        }

        (AnimatorInflater.loadAnimator(context, R.animator.results_screen_view_animation) as AnimatorSet).apply {
          setTarget(binding.restartQuizButton)
            start()
        }

        binding.resultsText.text = arguments?.getString(ARG_PARAM1)

        binding.restartQuizButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultsScreen_to_surveyScreen)
        }
    }
}