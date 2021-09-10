package com.icdominguez.socialmediagamerkotlin.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.CollectionReference
import com.icdominguez.socialmediagamerkotlin.R
import com.icdominguez.socialmediagamerkotlin.chat.ChatFragment
import com.icdominguez.socialmediagamerkotlin.common.Constants
import com.icdominguez.socialmediagamerkotlin.common.ResultOf
import com.icdominguez.socialmediagamerkotlin.databinding.ActivityHomeBinding
import com.icdominguez.socialmediagamerkotlin.filter.FilterFragment
import com.icdominguez.socialmediagamerkotlin.profile.ProfileFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


        supportFragmentManager.beginTransaction().replace(R.id.HomeContainer, HomeFragment()).addToBackStack(null).commit()

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.HomeContainer, HomeFragment()).addToBackStack(null).commit()
                    true
                }
                R.id.nav_filter -> {
                    supportFragmentManager.beginTransaction().replace(R.id.HomeContainer, FilterFragment()).addToBackStack(null).commit()
                    true
                }
                R.id.nav_chat -> {
                    supportFragmentManager.beginTransaction().replace(R.id.HomeContainer, ChatFragment()).addToBackStack(null).commit()
                    true
                }
                R.id.nav_profile -> {
                    supportFragmentManager.beginTransaction().replace(R.id.HomeContainer, ProfileFragment()).addToBackStack(null).commit()
                    true
                }
                else ->  false
            }
        }
    }
}