package com.example.wishem.ui.addevent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wishem.R
import com.example.wishem.databinding.FragmentAddEventBinding
import com.example.wishem.utils.Result
import com.example.wishem.utils.UtilsFunctions.showAddSuccessSB
import com.example.wishem.utils.UtilsFunctions.showErrorSB
import com.example.wishem.viewmodel.OccasionViewModel

class AddEventFragment : Fragment() {

    private lateinit var binding : FragmentAddEventBinding
    private lateinit var viewModel : OccasionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddEventBinding.inflate(inflater, container, false)
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

        //back navigation from toolbar
        binding.toolBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        //dates dropdown
        setupDateDropDown()
        //months dropdown
        setupMonthDropDown()

        binding.btnAdd.setOnClickListener {
            val name = binding.etName.text.toString()
            val date = binding.etDate.text.toString()
            val month = binding.etMonth.text.toString()
            val chipId = binding.cgOccasion.checkedChipId

            viewModel.addOccasion(name, date, month, chipId)
        }
    }

    private fun observeLiveData() {
        viewModel.addingResult.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Error -> binding.root.showErrorSB(it.msg)
                is Result.Success -> {
                    binding.root.showAddSuccessSB()
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun setupDateDropDown() {
        val dates = mutableListOf<Int>()
        for(i in 1..31) {
            dates.add(i)
        }
        val datesAdapter = ArrayAdapter(requireContext(), R.layout.holder_dropdown_items, dates)
        binding.etDate.setAdapter(datesAdapter)

    }

    private fun setupMonthDropDown() {
        val months = mutableListOf<Int>()
        for(i in 1..12) {
            months.add(i)
        }
        val monthsAdapter = ArrayAdapter(requireContext(), R.layout.holder_dropdown_items, months)
        binding.etMonth.setAdapter(monthsAdapter)
    }


}