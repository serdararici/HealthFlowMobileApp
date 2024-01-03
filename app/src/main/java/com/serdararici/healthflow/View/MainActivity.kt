package com.serdararici.healthflow.View

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.serdararici.healthflow.R
import com.serdararici.healthflow.ViewModel.AuthViewModel
import com.serdararici.healthflow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var binding: ActivityMainBinding
    private val viewModel: AuthViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        NavigationUI.setupWithNavController(binding.BottomNavigationView, navHostFragment.navController)
        NavigationUI.setupWithNavController(binding.navigationView, navHostFragment.navController)

        //binding.materialToolbar.title
        //binding.materialToolbar.subtitle
        //binding.materialToolbar.setLogo(R.drawable.healthflowlogo)

        //to connect nav_drawer and fragements
        setSupportActionBar(binding.materialToolbar)
        val toggle = ActionBarDrawerToggle(this, binding.drawer,binding.materialToolbar,R.string.nav_open, R.string.nav_close)
        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()


        //binding.navigationView.setNavigationItemSelectedListener(this)

        /*binding.BottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.mainFragment -> replaceFragment(MainFragment())
                R.id.medicine -> replaceFragment(MedicineFragment())
                R.id.diabetes -> replaceFragment(DiabetesFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
            }
            true
        }*/
    }
    private fun replaceFragment(fragment : Fragment) {
        val fragementManager = supportFragmentManager
        val fragmentTransaction = fragementManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView,fragment)
            .addToBackStack("name")
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        if (binding.drawer.isDrawerOpen((GravityCompat.START))) {
            binding.drawer.closeDrawer(GravityCompat.START)
        }else {
            super.onBackPressed()
        }



    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.mainFragment -> replaceFragment(MainFragment())
            R.id.medicineFragment -> replaceFragment(MedicineFragment())
            R.id.diabetesFragment -> replaceFragment(DiabetesFragment())
            R.id.profileFragment -> replaceFragment(ProfileFragment())
            R.id.settingsFragment -> replaceFragment(SettingsFragment())
            R.id.signOut -> signOut()
        }
        binding.drawer.closeDrawer(GravityCompat.START)
        return true
    }

    fun signOut () {
        val user = viewModel.currentUserViewModel()?.email.toString()
        viewModel.signOutViewModel(){ success ->
            if (success) {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                Activity().finish()
                Toast.makeText(this, "$user hesabından çıkış yapıldı.",Toast.LENGTH_LONG).show()
            }
        }
    }
}