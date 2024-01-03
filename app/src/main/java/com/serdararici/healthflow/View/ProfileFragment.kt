package com.serdararici.healthflow.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.serdararici.healthflow.Model.Medicine
import com.serdararici.healthflow.Model.Profile
import com.serdararici.healthflow.R
import com.serdararici.healthflow.ViewModel.AuthViewModel
import com.serdararici.healthflow.ViewModel.ProfileViewModel
import com.serdararici.healthflow.databinding.FragmentProfileBinding
import com.serdararici.healthflow.databinding.FragmentSettingsBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding?=null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val viewModelAuth: AuthViewModel by viewModels()
    private val viewModelProfile: ProfileViewModel by viewModels()
    private var profileList = ArrayList<Profile>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(com.serdararici.healthflow.R.id.BottomNavigationView)
        bottomNavigationView.visibility = View.VISIBLE

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.profile)
        navController = Navigation.findNavController(view)

        /*viewModelProfile.profileListLive.observe(viewLifecycleOwner, Observer {list->
            profileList = list
        })

        viewModelProfile.getProfiles()
        if (profileList != null && profileList!!.isNotEmpty()){
            for (position in 0 until profileList!!.size) {
                var profile = profileList!![position]
                binding.tvUserName.setText(profile.userName)
                binding.tvEmail.setText(profile.userEmail)
                binding.tvPhoneResult.setText(profile.phoneNumber)
                binding.tvBirthDateResult.setText(profile.birthDate)
                binding.tvHeightResult.setText(profile.height.toString())
                binding.tvWeightResult.setText(profile.weight.toString())
            }
        }else{
            Log.e("ProfileFragment", "LiveData'dan gelen liste null veya boş.")
        }*/

        viewModelProfile.profileListLive.observe(viewLifecycleOwner, Observer { profileList ->
            if (profileList != null && profileList.isNotEmpty()) {
                val profile = profileList[0]
                //updateUIWithProfileData(profile)
                binding.tvUserName.text = profile.userName
                binding.tvEmail.text = profile.userEmail
                binding.tvPhoneResult.text = profile.phoneNumber
                binding.tvBirthDateResult.text = profile.birthDate
                binding.tvHeightResult.text = profile.height.toString()
                binding.tvWeightResult.text = profile.weight.toString()
                val ageString = getString(R.string.yourAge)
                var age= viewModelProfile.caculateAge(profile.birthDate!!).toString()
                binding.tvAge.text = ageString +" " + age

                binding.cardViewGoToEditProfile.setOnClickListener{
                    val action = ProfileFragmentDirections.actionProfileFragmentToProfileEditFragment(profile)
                    navController.navigate(action)
                }
            }
        })

        /*viewModelProfile.getProfileViewModel()
        profileList = viewModelProfile.profileList
        binding.tvUserName.setText(profileList[0].userName)
        binding.tvEmail.setText(profileList[0].userEmail)
        binding.tvPhoneResult.setText(profileList[0].phoneNumber)
        binding.tvBirthDateResult.setText(profileList[0].birthDate)
        binding.tvHeightResult.setText(profileList[0].height.toString())
        binding.tvWeightResult.setText(profileList[0].weight.toString())*/



    }

    override fun onResume() {
        super.onResume()
        viewModelProfile.getProfiles()
    }

}