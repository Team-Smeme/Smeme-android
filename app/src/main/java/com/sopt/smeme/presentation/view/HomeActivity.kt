package com.sopt.smeme.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sopt.smeme.R
import com.sopt.smeme.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.home_container)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.home_container, MyDiaryHomeFragment())
                .commit()

        }

        binding.bnvMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_my_diary -> {
                    changeFragment(MyDiaryHomeFragment())
                }
                R.id.ic_explore -> {
                    TODO()
                }
                R.id.ic_save -> {
                    TODO()
                }
                else -> error("item id: {${it.itemId} is cannot be selected")
            }
            return@setOnItemSelectedListener true
        }

    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.home_container, fragment)
            .commit()

    }

}