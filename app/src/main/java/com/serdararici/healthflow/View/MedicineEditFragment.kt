package com.serdararici.healthflow.View

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Timestamp
import com.serdararici.healthflow.Model.Medicine
import com.serdararici.healthflow.R
import com.serdararici.healthflow.ViewModel.MedicineEditViewModel
import com.serdararici.healthflow.adapter.MedicineAdapter
import com.serdararici.healthflow.databinding.FragmentMedicineEditBinding
import java.util.Calendar


class MedicineEditFragment : Fragment() {
    private var _binding: FragmentMedicineEditBinding?=null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val viewModel: MedicineEditViewModel by viewModels()
    private lateinit var medicineList: ArrayList<Medicine>
    private lateinit var medicineAdapter: MedicineAdapter
    private val numberOfMedicine = ArrayList<String>()
    private lateinit var adapter:ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bottomNavigationView =
            requireActivity()!!.findViewById<BottomNavigationView>(com.serdararici.healthflow.R.id.BottomNavigationView)
        bottomNavigationView.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentMedicineEditBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.medicine)

        navController = Navigation.findNavController(view)
        numberOfMedicine.add("Yarım")
        numberOfMedicine.add("Tam")
        numberOfMedicine.add("İki")
        numberOfMedicine.add("Üç")
        numberOfMedicine.add("Dört")

        var medicineOfNumber = " "
        adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,android.R.id.text1,numberOfMedicine)
        binding.spinnerMedicineNumbers.adapter= adapter

        binding.spinnerMedicineNumbers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, index: Int, p3: Long) {
                medicineOfNumber = "${numberOfMedicine[index]}"
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        //binding.editTextTime.setText(" ")
        binding.editTextTime.setOnClickListener{
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val timePicker :TimePickerDialog

            timePicker = TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener{timePicker, h, m ->
                binding.editTextTime.setText("$h : $m")
            }, hour, minute,true)

            timePicker.setTitle(R.string.choose_time)
            val set=getString(R.string.set)
            val cancel=getString(R.string.cancel)
            timePicker.setButton(DialogInterface.BUTTON_POSITIVE, set, timePicker)
            timePicker.setButton(DialogInterface.BUTTON_NEGATIVE, cancel, timePicker)

            timePicker.show()
        }

        //binding.editTextDate.setText(" ")
        binding.editTextDate.setOnClickListener{
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{ datePicker, y, m, d ->
                binding.editTextDate.setText(("$d/${m+1}/$y"))
            },year,month,day)

            datePicker.setTitle(R.string.choose_date)
            val set=getString(R.string.set)
            val cancel=getString(R.string.cancel)
            datePicker.setButton(DialogInterface.BUTTON_POSITIVE, set, datePicker)
            datePicker.setButton(DialogInterface.BUTTON_NEGATIVE, cancel, datePicker)

            datePicker.show()


            binding.btnMedicineSave.setOnClickListener{
                var medicineName=binding.etMedicineName.text.toString()
                var medicineDetails=binding.etMedicineDetails.text.toString()
                var medicineNumbers= medicineOfNumber
                var medicineDate=binding.editTextDate.text.toString()
                var medicineTime=binding.editTextTime.text.toString()

                viewModel.addMedicineViewModel(medicineName,medicineDetails,medicineNumbers,medicineDate,medicineTime){onComplete ->
                    if(onComplete){
                        Toast.makeText(requireContext(), R.string.mediceneAdded, Toast.LENGTH_LONG).show()
                        navController.navigate(R.id.action_medicineEditFragment_to_medicineFragment)
                    }else{
                        Toast.makeText(requireContext(), R.string.mediceneAddFailed, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(com.serdararici.healthflow.R.id.BottomNavigationView)
        bottomNavigationView.visibility = View.VISIBLE
        _binding = null
    }

}