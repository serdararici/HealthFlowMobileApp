package com.serdararici.healthflow.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.serdararici.healthflow.R
import com.serdararici.healthflow.ViewModel.AuthViewModel
import com.serdararici.healthflow.databinding.FragmentMainBinding
import com.serdararici.healthflow.databinding.FragmentMedicineBinding

class MedicineFragment : Fragment() {
    private var _binding: FragmentMedicineBinding?=null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //(activity as? AppCompatActivity)?.supportActionBar?.title = "title"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentMedicineBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toolbar = (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.medicine)
    }

}