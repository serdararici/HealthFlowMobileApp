package com.serdararici.healthflow.View

import android.graphics.Rect
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.serdararici.healthflow.R
import com.serdararici.healthflow.ViewModel.AuthViewModel
import com.serdararici.healthflow.ViewModel.BodyMassIndexViewModel
import com.serdararici.healthflow.ViewModel.ProfileViewModel
import com.serdararici.healthflow.databinding.FragmentBodyMassIndexBinding
import com.serdararici.healthflow.databinding.FragmentSignUpBinding

class BodyMassIndexFragment : Fragment() {
    private var _binding: FragmentBodyMassIndexBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    private val viewModelProfile: ProfileViewModel by viewModels()
    private val viewModelBodyMassIndex: BodyMassIndexViewModel by viewModels()

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
        _binding = FragmentBodyMassIndexBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val toolbar = (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.bodyMassIndex)

        binding.btnCalculate.setOnClickListener {
            var height = binding.etHeight.text.toString().toDoubleOrNull()
            var weight = binding.etWeight.text.toString().toDoubleOrNull()
            if(checkAll()){
                val result = viewModelBodyMassIndex.calculateBodyMassIndex(height!!,weight!!)
                val formattedResult = String.format("%.2f", result)
                var resulText =""
                if(result<18.5){
                    resulText = formattedResult + " ---> " + getString(R.string.underWeight)
                }else if (18.5<=result && result<24.9){
                    resulText = formattedResult + " ---> " + getString(R.string.normalWeight)
                }else if(25<=result && result<29.9){
                    resulText = formattedResult + " ---> " + getString(R.string.overWeight)
                }else if(30<=result && result<34.9){
                    resulText = formattedResult + " ---> " + getString(R.string.obesity1)
                }else if(35<=result && result<39.9){
                    resulText = formattedResult + " ---> " + getString(R.string.obesity2)
                }else if(40<=result){
                    resulText = formattedResult + " ---> " + getString(R.string.obesity3)
                }else{
                    resulText = " "
                }
                binding.tvResult.text = getString(R.string.result) +"${resulText}"
            }
        }
    }

    private fun checkAll():Boolean {
        val height = binding.etHeight.text.toString()
        val weight = binding.etWeight.text.toString()
        clearErrors()
        if(height == ""){
            binding.textInputHeight.error = getString(R.string.requiredHeightBmi)
            Toast.makeText(requireContext(), R.string.requiredHeightBmi, Toast.LENGTH_LONG).show()
            return false
        }
        if(weight == ""){
            binding.textInputWeight.error = getString(R.string.requiredWeightBmi)
            Toast.makeText(requireContext(), R.string.requiredWeightBmi, Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun clearErrors() {
        binding.textInputHeight.error = null
        binding.textInputWeight.error = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(com.serdararici.healthflow.R.id.BottomNavigationView)
        bottomNavigationView.visibility = View.VISIBLE
        _binding = null
    }

}