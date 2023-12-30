package com.serdararici.healthflow.View

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.serdararici.healthflow.Model.Medicine
import com.serdararici.healthflow.R
import com.serdararici.healthflow.ViewModel.MedicineEditViewModel
import com.serdararici.healthflow.ViewModel.MedicineUpdateViewModel
import com.serdararici.healthflow.ViewModel.MedicineViewModel
import com.serdararici.healthflow.databinding.FragmentMedicineBinding
import com.serdararici.healthflow.databinding.FragmentMedicineEditBinding
import com.serdararici.healthflow.databinding.FragmentMedicineUpdateBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class MedicineUpdateFragment : Fragment() {
    private var _binding: FragmentMedicineUpdateBinding?=null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var adapter:ArrayAdapter<String>
    private val numberOfMedicine = ArrayList<String>()
    private lateinit var medicineList: ArrayList<Medicine>
    private val viewModelMedicine: MedicineViewModel by viewModels()
    private val viewModelMedicineEdit: MedicineEditViewModel by viewModels()
    private val viewModelMedicineUpdate: MedicineUpdateViewModel by viewModels()

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
        _binding= FragmentMedicineUpdateBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.medicine)

        val bundle:MedicineUpdateFragmentArgs by navArgs()
        val medicine = bundle.medicine
        binding.etMedicineName.setText(medicine.medicineName)
        binding.etMedicineDetails.setText(medicine.medicineDetails)
        binding.editTextDate.setText(medicine.medicineDate)
        binding.editTextTime.setText(medicine.medicineTime)

        navController = Navigation.findNavController(view)

        numberOfMedicine.add("Yarım")
        numberOfMedicine.add("Tam")
        numberOfMedicine.add("İki")
        numberOfMedicine.add("Üç")
        numberOfMedicine.add("Dört")

        var medicineOfNumber = ""
        adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,android.R.id.text1,numberOfMedicine)
        binding.spinnerMedicineNumbers.adapter= adapter

        val initialSelection = medicine.medicineOfNumber.toString()
        val initialPosition = numberOfMedicine.indexOf(initialSelection)

        if (initialPosition != -1) {
            binding.spinnerMedicineNumbers.setSelection(initialPosition)
        }
        binding.spinnerMedicineNumbers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, index: Int, p3: Long) {
                medicineOfNumber = "${numberOfMedicine[index]}"
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.editTextTime.setOnClickListener{
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val timePicker : TimePickerDialog

            timePicker = TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener{ timePicker, h, m ->
                binding.editTextTime.setText("$h : $m")
            }, hour, minute,true)

            timePicker.setTitle(R.string.choose_time)
            val set=getString(R.string.set)
            val cancel=getString(R.string.cancel)
            timePicker.setButton(DialogInterface.BUTTON_POSITIVE, set, timePicker)
            timePicker.setButton(DialogInterface.BUTTON_NEGATIVE, cancel, timePicker)

            timePicker.show()
        }

        binding.editTextDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
                    binding.editTextDate.setText(("$d/${m + 1}/$y"))
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

        binding.btnMedicineSave.setOnClickListener{
            var medicineName=binding.etMedicineName.text.toString()
            var medicineDetails=binding.etMedicineDetails.text.toString()
            var medicineNumbers= medicineOfNumber
            var medicineDate=binding.editTextDate.text.toString()
            var medicineTime=binding.editTextTime.text.toString()

            /*viewModelMedicine.getMedicineViewModel(){success,message->
                medicineList = viewModelMedicine.medicineList
            }*/
            var medicineId= medicine.medicineId
            viewModelMedicineUpdate.medicineUpdateViewModel(medicineId!!, medicineName, medicineDetails, medicineNumbers, medicineDate, medicineTime)
            navController.navigate(R.id.action_medicineUpdateFragment_to_medicineFragment)
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