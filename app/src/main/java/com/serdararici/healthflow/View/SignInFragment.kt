package com.serdararici.healthflow.View

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.serdararici.healthflow.R
import com.serdararici.healthflow.ViewModel.AuthViewModel
import com.serdararici.healthflow.ViewModel.ProfileViewModel
import com.serdararici.healthflow.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding?=null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val viewModel: AuthViewModel by viewModels()
    private val viewModelProfile: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = viewModel.currentUserViewModel()
        //kullanıcı giriş yaptıysa tekrar giriş ekranı gelmesin
        if(user !=null){
            val intent = Intent(requireContext(),MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentSignInBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)

        binding.tvSginInToSignUp.setOnClickListener{
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.btnSignIn.setOnClickListener{
            val email=binding.etEmailAdresSignIn.text.toString()
            val password=binding.etPasswordSignIn.text.toString()

            if(checkAll()){
                viewModel.signInViewModel(email,password){ success, message ->
                    if (success) {
                        // Giriş başarılı, ek işlemleri yapabilirsiniz.
                        val user = viewModel.currentUserViewModel()?.email.toString()
                        viewModelProfile.profileListLive.observe(viewLifecycleOwner, Observer { profileList ->
                            if (profileList != null && profileList.isNotEmpty()) {
                                val profile = profileList[0]
                                val userName = profile.userName

                                Toast.makeText(requireContext(), getString(R.string.welcome)+" $userName",Toast.LENGTH_LONG).show()
                                val intent = Intent(requireContext(), MainActivity::class.java)
                                startActivity(intent)
                                requireActivity().finish()
                                //navController.navigate(R.id.action_signInFragment_to_mainFragment)
                            }
                        })
                    } else {
                        // Giriş başarısız, kullanıcıya hata mesajı gösterilebilir.
                        Toast.makeText(requireContext(), getString(R.string.signInFailed), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.tvForgotPassword.setOnClickListener{
            val action = SignInFragmentDirections.actionSignInFragmentToForgetPasswordFragment()
            navController.navigate(action)
        }
    }


    private fun checkAll():Boolean {
        val email = binding.etEmailAdresSignIn.text.toString()
        val password = binding.etPasswordSignIn.text.toString()
        clearErrors()
        if(email == ""){
            binding.textInputEmailSignIn.error = getString(R.string.requiredEmail)
            Toast.makeText(requireContext(), R.string.requiredEmail, Toast.LENGTH_LONG).show()
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.textInputEmailSignIn.error = getString(R.string.checkEmailFormat)
            Toast.makeText(requireContext(), R.string.checkEmailFormat, Toast.LENGTH_LONG).show()
            return false
        }
        if(password == ""){
            binding.textInputPasswordSignIn.error = getString(R.string.requiredPassword)
            Toast.makeText(requireContext(), R.string.requiredPassword, Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
    private fun clearErrors() {
        binding.textInputEmailSignIn.error = null
        binding.textInputPasswordSignIn.error = null
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}