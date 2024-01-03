package com.serdararici.healthflow.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.serdararici.healthflow.R
import com.serdararici.healthflow.ViewModel.AuthViewModel
import com.serdararici.healthflow.ViewModel.ProfileViewModel
import com.serdararici.healthflow.databinding.FragmentBodyMassIndexBinding
import com.serdararici.healthflow.databinding.FragmentSignUpBinding

class BodyMassIndexFragment : Fragment() {
    private var _binding: FragmentBodyMassIndexBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    private val viewModelProfile: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    }

}