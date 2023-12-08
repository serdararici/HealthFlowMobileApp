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
import com.serdararici.healthflow.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding?=null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val viewModel: AuthViewModel by viewModels()

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

            if(email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.signInViewModel(email,password){ success, message ->

                    if (success) {
                        // Giriş başarılı, ek işlemleri yapabilirsiniz.
                        val user = viewModel.currentUserViewModel()?.email.toString()
                        Toast.makeText(requireContext(), "Hoşgeldin: $user",Toast.LENGTH_LONG).show()
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                        //navController.navigate(R.id.action_signInFragment_to_mainFragment)
                    } else {
                        // Giriş başarısız, kullanıcıya hata mesajı gösterilebilir.
                        Toast.makeText(requireContext(), "Giriş başarısız!: $message", Toast.LENGTH_SHORT).show()
                    }

                }
            } else {
                Toast.makeText(requireContext(), "Email ve şifre alanları boş bırakılamaz.", Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}