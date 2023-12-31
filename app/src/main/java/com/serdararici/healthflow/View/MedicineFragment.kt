package com.serdararici.healthflow.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.serdararici.healthflow.Model.Medicine
import com.serdararici.healthflow.R
import com.serdararici.healthflow.ViewModel.AuthViewModel
import com.serdararici.healthflow.ViewModel.MedicineEditViewModel
import com.serdararici.healthflow.ViewModel.MedicineViewModel
import com.serdararici.healthflow.adapter.MedicineAdapter
import com.serdararici.healthflow.databinding.FragmentMainBinding
import com.serdararici.healthflow.databinding.FragmentMedicineBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MedicineFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentMedicineBinding?=null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val viewModelAuth: AuthViewModel by viewModels()
    private val viewModelMedicine: MedicineViewModel by viewModels()
    private lateinit var medicineList: ArrayList<Medicine>
    private lateinit var medicineAdapter: MedicineAdapter
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
        _binding= FragmentMedicineBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        val toolbar = (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.medicine)

        requireActivity().addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_search_menu,menu)

                val item = menu.findItem(R.id.action_search)
                val searchView = item.actionView as SearchView
                searchView.setOnQueryTextListener(this@MedicineFragment)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        },viewLifecycleOwner,Lifecycle.State.RESUMED)


        var layoutManager = LinearLayoutManager(context)
        binding.medicineRecyclerView.setHasFixedSize(true)
        binding.medicineRecyclerView.layoutManager = layoutManager

        viewModelMedicine.medicineListLive.observe(viewLifecycleOwner){
            medicineAdapter = MedicineAdapter(requireContext(), it, viewModelMedicine)
            binding.medicineRecyclerView.adapter = medicineAdapter
        }

        binding.floatingActionButton.setOnClickListener {

            navController.navigate(R.id.action_medicineFragment_to_medicineEditFragment)
            //replaceFragment(MedicineEditFragment())
        }
    }
    private fun replaceFragment(fragment : Fragment) {
        val fragementManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragementManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView,fragment)
            .addToBackStack("name")
        fragmentTransaction.commit()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        viewModelMedicine.medicineSearchViewModel(query)
        return true
    }
    override fun onQueryTextChange(newText: String): Boolean {
        viewModelMedicine.medicineSearchViewModel(newText)
        return true
    }

}