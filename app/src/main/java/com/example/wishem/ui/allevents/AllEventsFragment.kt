package com.example.wishem.ui.allevents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wishem.R
import com.example.wishem.databinding.FragmentAllEventsBinding
import com.example.wishem.utils.UtilsFunctions.getCurrentDateMonth
import com.example.wishem.viewmodel.OccasionViewModel
import java.text.SimpleDateFormat
import java.util.*

class AllEventsFragment : Fragment() {


    private lateinit var binding : FragmentAllEventsBinding
    private lateinit var viewModel : OccasionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupViews()
    }

    private fun setupViewModel() {
        viewModel = OccasionViewModel.getOccasionViewModel(requireActivity(), requireActivity().application, requireContext())
    }
    private fun setupViews() {
        //setting up date
        getCurrentDate()

        //click listeners
        binding.btnAddEvent.setOnClickListener{
            findNavController().navigate(R.id.action_allEventsFragment_to_addEventFragment)
        }
    }

    private fun getCurrentDate() {
        binding.date = "Today : ${getCurrentDateMonth()}"
    }
}