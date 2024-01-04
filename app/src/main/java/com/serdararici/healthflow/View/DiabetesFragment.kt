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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.serdararici.healthflow.Model.Diabetes
import com.serdararici.healthflow.R
import com.serdararici.healthflow.ViewModel.DiabetesViewModel
import com.serdararici.healthflow.ViewModel.MedicineEditViewModel
import com.serdararici.healthflow.adapter.DiabetesAdapter
import com.serdararici.healthflow.databinding.FragmentDiabetesBinding
import com.serdararici.healthflow.databinding.FragmentMedicineBinding

class DiabetesFragment : Fragment() {
    private var _binding: FragmentDiabetesBinding?=null
    private val binding get() = _binding!!
    private val viewModelDiabetes: DiabetesViewModel by viewModels()
    private lateinit var navController: NavController
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
        _binding= FragmentDiabetesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.diabetesTracking)

        navController = Navigation.findNavController(view)

        binding.recyclerViewDiabetes.layoutManager = LinearLayoutManager(requireContext())

        viewModelDiabetes.diabetesListLive.observe(viewLifecycleOwner){
            val adapter = DiabetesAdapter(requireContext(), it,viewModelDiabetes)
            binding.recyclerViewDiabetes.adapter = adapter
        }

        binding.floatingActionButtonDiabetes.setOnClickListener{
            navController.navigate(R.id.action_diabetesFragment_to_diabetesEditFragment)
        }
    }

    /*override fun onResume() {
        super.onResume()
        viewModelDiabetes.getAllDiabetesViewModel()
    }*/
}