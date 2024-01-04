package com.serdararici.healthflow.View

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.serdararici.healthflow.R
import com.serdararici.healthflow.ViewModel.DiabetesEditViewModel
import com.serdararici.healthflow.ViewModel.DiabetesViewModel
import com.serdararici.healthflow.databinding.FragmentDiabetesBinding
import com.serdararici.healthflow.databinding.FragmentDiabetesEditBinding
import java.util.Calendar

class DiabetesEditFragment : Fragment() {
    private var _binding: FragmentDiabetesEditBinding?=null
    private val binding get() = _binding!!
    private val viewModelDiabetesEdit: DiabetesEditViewModel by viewModels()
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentDiabetesEditBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.etDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
                    binding.etDate.setText(("$d/${m + 1}/$y"))
                },
                year,
                month,
                day
            )
            datePicker.setTitle(R.string.choose_date)
            val set = getString(R.string.set)
            val cancel = getString(R.string.cancel)
            datePicker.setButton(DialogInterface.BUTTON_POSITIVE, set, datePicker)
            datePicker.setButton(DialogInterface.BUTTON_NEGATIVE, cancel, datePicker)

            datePicker.show()
        }

        binding.etTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val timePicker : TimePickerDialog

            timePicker = TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener{ timePicker, h, m ->
                binding.etTime.setText("$h : $m")
            }, hour, minute,true)
            timePicker.setTitle(R.string.choose_time)
            val set=getString(R.string.set)
            val cancel=getString(R.string.cancel)
            timePicker.setButton(DialogInterface.BUTTON_POSITIVE, set, timePicker)
            timePicker.setButton(DialogInterface.BUTTON_NEGATIVE, cancel, timePicker)

            timePicker.show()
        }

        binding.btnSaveDiabetesEdit.setOnClickListener {
            var diabetesResult = binding.etDiabetesResult.text.toString()
            var rbHungry = binding.radioButtonHungry.isChecked
            var rbFull = binding.radioButtonFull.isChecked
            var diabetesDate = binding.etDate.text.toString()
            var diabetesTime = binding.etTime.text.toString()
            var hungryLevel=""
            if(checkAll()){

                if (rbHungry==true  && rbFull==false){
                    hungryLevel= getString(R.string.hungry)
                }
                if (rbFull==true && rbHungry==false){
                    hungryLevel= getString(R.string.full)
                }
                viewModelDiabetesEdit.addDiabetesViewModel(diabetesResult,hungryLevel,diabetesDate,diabetesTime)
                navController.navigate(R.id.action_diabetesEditFragment_to_diabetesFragment)
            }
        }
    }


    private fun checkAll():Boolean {
        val diabetesResult = binding.etDiabetesResult.text.toString()
        val rbHungry = binding.radioButtonHungry.isChecked
        val rbFull = binding.radioButtonFull.isChecked
        val diabetesDate = binding.etDate.text.toString()
        val diabetesTime = binding.etTime.text.toString()
        clearErrors()
        if(diabetesResult == ""){
            binding.textInputDiabetesResult.error = getString(R.string.diabetesResultRequired)
            Toast.makeText(requireContext(), R.string.diabetesResultRequired, Toast.LENGTH_LONG).show()
            return false
        }
        if(rbHungry == false && rbFull == false){
            //binding.textInputWeight.error = getString(R.string.requiredWeightBmi)
            Toast.makeText(requireContext(), R.string.diabetesHungryLevelRequired, Toast.LENGTH_LONG).show()
            return false
        }
        if(diabetesDate == ""){
            binding.textInputDate.error = getString(R.string.diabetesDateRequired)
            Toast.makeText(requireContext(), R.string.diabetesDateRequired, Toast.LENGTH_LONG).show()
            return false
        }
        if(diabetesTime == ""){
            binding.textInputTime.error = getString(R.string.diabetesTimeRequired)
            Toast.makeText(requireContext(), R.string.diabetesTimeRequired, Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun clearErrors() {
        binding.textInputDiabetesResult.error = null
        binding.textInputDate.error = null
        binding.textInputTime.error = null
        binding.radioButtonHungry.error = null
        binding.radioButtonFull.error = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}