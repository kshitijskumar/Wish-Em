package com.example.wishem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.wishem.databinding.ActivityFeaturesBinding

class FeaturesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFeaturesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeaturesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupViews() {
        //navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }
}