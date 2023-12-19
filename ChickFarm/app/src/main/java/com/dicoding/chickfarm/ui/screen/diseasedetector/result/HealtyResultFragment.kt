package com.dicoding.chickfarm.ui.screen.diseasedetector.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.dicoding.chickfarm.databinding.FragmentHealtyResultBinding


class HealtyResultFragment : DialogFragment() {
    private lateinit var binding: FragmentHealtyResultBinding



    companion object {
        const val ARG_RESULT_TEXT = "arg_result_text"

        fun newInstance(resultText: String): HealtyResultFragment {
            val fragment = HealtyResultFragment()
            val args = Bundle()
            args.putString(ARG_RESULT_TEXT, resultText)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHealtyResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val resultText = it.getString(ARG_RESULT_TEXT)
            binding.resultTextView.text = resultText
        }

        binding.batalBtn.setOnClickListener {
            dismiss()
        }
    }
}