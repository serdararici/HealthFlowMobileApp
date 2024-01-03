package com.serdararici.healthflow.View

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.serdararici.healthflow.R
import com.serdararici.healthflow.ViewModel.MedicineEditViewModel
import com.serdararici.healthflow.ViewModel.ProfileEditViewModel
import com.serdararici.healthflow.databinding.FragmentMedicineBinding
import com.serdararici.healthflow.databinding.FragmentProfileEditBinding
import java.util.Calendar

class ProfileEditFragment : Fragment() {
    private var _binding: FragmentProfileEditBinding?=null
    private val binding get() = _binding!!
    private val viewModelProfileEdit: ProfileEditViewModel by viewModels()
    private lateinit var navController: NavController
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
        _binding= FragmentProfileEditBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val bundle:ProfileEditFragmentArgs by navArgs()
        val profile = bundle.profile

        binding.etNameSurname.setText(profile.userName)
        binding.etPhoneNo.setText(profile.phoneNumber)
        binding.etBirthDate.setText(profile.birthDate)
        binding.etHeight.setText(profile.height.toString())
        binding.etWeight.setText(profile.weight.toString())

        binding.etBirthDate.setOnClickListener{
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
                    binding.etBirthDate.setText(("$d/${m + 1}/$y"))
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


        binding.btnSave.setOnClickListener {
            var userName= binding.etNameSurname.text.toString()
            var phoneNumber= binding.etPhoneNo.text.toString()
            var birthDate= binding.etBirthDate.text.toString()
            var height= binding.etHeight.text.toString().toDoubleOrNull()
            var weight= binding.etWeight.text.toString().toDoubleOrNull()
            //var profileId= profile.profileId
            //var userEmail=profile.userEmail
            var profileId= profile.profileId
            var userEmail=profile.userEmail
            viewModelProfileEdit.updateProfileViewModel(profileId!!,userName,userEmail!!, phoneNumber,birthDate,height!!,weight!!)
            navController.navigate(R.id.action_profileEditFragment_to_profileFragment)
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