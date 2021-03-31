package com.example.wishem

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.example.wishem.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        setupViews()
    }

    private fun setupViews() {
        binding.splashLottie.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                Log.d("MainActivity", "Animation started")
            }

            override fun onAnimationEnd(animation: Animator?) {
                binding.apply {
                    tvTop.visibility = View.GONE
                    tvBottom.visibility = View.GONE
                }
                val intent = Intent(this@MainActivity, FeaturesActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAnimationCancel(animation: Animator?) {
                Log.d("MainActivity", "Animation cancelled")
            }

            override fun onAnimationRepeat(animation: Animator?) {
                Log.d("MainActivity", "Animation repeated")
            }
        })
    }
}