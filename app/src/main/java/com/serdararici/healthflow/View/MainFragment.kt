package com.serdararici.healthflow.View

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.serdararici.healthflow.R
import com.serdararici.healthflow.ViewModel.AuthViewModel
import com.serdararici.healthflow.databinding.FragmentMainBinding
import com.serdararici.healthflow.databinding.FragmentSignInBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding?=null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)
        val user = viewModel.currentUserViewModel()?.email.toString()

        binding.btnSignOut.setOnClickListener{
            viewModel.signOutViewModel(){ success ->
              if (success) {
                  val intent = Intent(requireContext(), AuthActivity::class.java)
                  startActivity(intent)
                  requireActivity().finish()
                  Toast.makeText(requireContext(), "$user hesabından çıkış yapıldı.",Toast.LENGTH_LONG).show()
              }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}