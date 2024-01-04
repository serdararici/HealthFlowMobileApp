package com.serdararici.healthflow.View

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.serdararici.healthflow.R
import com.serdararici.healthflow.ViewModel.AuthViewModel
import com.serdararici.healthflow.databinding.FragmentProfileEditBinding
import com.serdararici.healthflow.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding?=null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val viewModelAuth: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.settings)
        navController = Navigation.findNavController(view)

        val userEmail = viewModelAuth.currentUserViewModel()?.email.toString()
        binding.cardViewSignOut.setOnClickListener{
            navController.navigate(R.id.action_settingsFragment_to_signOutFragment)
            /*viewModelAuth.signOutViewModel(){ success ->
                if (success) {
                    val intent = Intent(requireContext(), AuthActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                    Toast.makeText(requireContext(), "$userEmail " + R.string.signedOut, Toast.LENGTH_LONG).show()
                }
            }*/
        }
    }
}