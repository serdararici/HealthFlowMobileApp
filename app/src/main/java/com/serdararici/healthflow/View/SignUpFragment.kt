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
import com.serdararici.healthflow.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
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
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmailAdressSignUp.text.toString()
            val password = binding.etPasswordSignUp.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.signUpViewModel(email, password) { success, message ->
                    if(success){
                        Toast.makeText(requireContext(), "Kayıt Başarılı", Toast.LENGTH_LONG).show()
                        val intent = Intent(requireActivity(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                        //navController.navigate(R.id.action_signUpFragment_to_signInFragment)
                    }else{
                        Toast.makeText(requireContext(), "Kayıt Başarısız $message", Toast.LENGTH_LONG ).show()
                    }
                }
            }else {
                Toast.makeText(requireContext(), "Email ve şifre alanları boş bırakılamaz.", Toast.LENGTH_LONG).show()
            }

        }

        binding.tvSignUpToSignIn.setOnClickListener {
            navController.navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}