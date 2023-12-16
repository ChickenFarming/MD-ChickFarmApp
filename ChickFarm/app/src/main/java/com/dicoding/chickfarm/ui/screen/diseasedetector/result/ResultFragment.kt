package com.dicoding.chickfarm.ui.screen.diseasedetector.result

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.chickfarm.MainActivity
import com.dicoding.chickfarm.data.Repository
import com.dicoding.chickfarm.ViewModelFactory
import com.dicoding.chickfarm.data.retrofit.ApiConfig
import com.dicoding.chickfarm.databinding.FragmentResultBinding
import com.dicoding.chickfarm.ui.screen.market.MarketViewModel

class ResultFragment : DialogFragment() {

    private lateinit var binding: FragmentResultBinding


    private lateinit var marketViewModel: MarketViewModel

    companion object {
        const val ARG_RESULT_TEXT = "arg_result_text"
        const val ARG_RESULT_DESC = "arg_result_desc"

        fun newInstance(resultText: String, desc: String): ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle()
            args.putString(ARG_RESULT_TEXT, resultText)
            args.putString(ARG_RESULT_DESC, desc)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = ViewModelFactory(repository = Repository.getInstance(ApiConfig().getApiService()), requireContext() )
        marketViewModel = ViewModelProvider(this, viewModelFactory).get(MarketViewModel::class.java)

        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val resultText = it.getString(ARG_RESULT_TEXT)
            binding.resultTextView.text = resultText
            binding.desc.text = it.getString(ARG_RESULT_DESC)
        }

        binding.batalBtn.setOnClickListener {
            dismiss()
        }
        binding.cariObatBtn.setOnClickListener {
            val searchValue = binding.resultTextView.text.toString()
//            marketViewModel.setSearchValue(searchValue.split(" ").firstOrNull().toString())

            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("searchValue", searchValue.split(" ").firstOrNull().toString() )
            intent.putExtra("navigateToMarket", true )

            startActivity(intent)
            requireActivity().finish()
        }
    }
}