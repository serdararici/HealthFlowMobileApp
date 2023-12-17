package com.serdararici.healthflow.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.serdararici.healthflow.R
import com.serdararici.healthflow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        binding.BottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(MainFragment())
                R.id.medicine -> replaceFragment(MedicineFragment())
                R.id.diabetes -> replaceFragment(DiabetesFragment())
                R.id.profile -> replaceFragment(ProfileFragment())

                else ->{

                }
            }

            true

        }
    }

    private fun replaceFragment(fragment : Fragment) {
        val fragementManager = supportFragmentManager
        val fragmentTransaction = fragementManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView,fragment)
        fragmentTransaction.commit()
    }
}