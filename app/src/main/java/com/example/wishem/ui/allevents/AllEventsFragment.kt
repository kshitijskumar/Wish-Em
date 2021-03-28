package com.example.wishem.ui.allevents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wishem.R
import com.example.wishem.databinding.FragmentAllEventsBinding
import com.example.wishem.ui.allevents.adapters.OccasionAdapter
import com.example.wishem.utils.Result
import com.example.wishem.utils.UtilsFunctions.getCurrentDateMonth
import com.example.wishem.viewmodel.OccasionViewModel

class AllEventsFragment : Fragment() {


    private lateinit var binding : FragmentAllEventsBinding
    private lateinit var viewModel : OccasionViewModel

    private val allAdapter by lazy {
        OccasionAdapter({}, false)
    }

    private val todaysAdapter by lazy {
        OccasionAdapter({

        }, true)
    }

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
        observeLiveData()
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

        //todays events recycler view
        binding.rvTodayEvents.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = todaysAdapter
        }

        //all events recycler view
        binding.rvAllEvents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = allAdapter
        }

    }

    private fun observeLiveData() {
        viewModel.todaysOccasion.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Success -> todaysAdapter.submitList(it.data)
            }
        }

        viewModel.allOccasion.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Success -> allAdapter.submitList(it.data)
            }
        }
    }

    private fun getCurrentDate() {
        binding.date = "Today : ${getCurrentDateMonth()}"
    }
}