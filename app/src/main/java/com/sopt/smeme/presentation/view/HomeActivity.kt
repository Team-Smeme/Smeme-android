package com.sopt.smeme.presentation.view

import android.graphics.Color
import androidx.fragment.app.FragmentManager
import com.sopt.smeme.R
import com.sopt.smeme.databinding.ActivityHomeBinding

class HomeActivity : ViewBoundActivity<ActivityHomeBinding>(R.layout.activity_home) {

    override fun constructLayout() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.home_container)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.home_container, MyDiaryHomeFragment())
                .commit()
        }
    }

    override fun listen() {
        binding.bnvMain.setOnItemSelectedListener {
            BottomMenu
                .from(it.title.toString())
                .changeFragment(supportFragmentManager)
            return@setOnItemSelectedListener true
        }
    }

    fun changeBackgroundColor(color: String) {
        binding.bnvMain.setBackgroundColor(Color.parseColor(color))
    }

    private enum class BottomMenu {
        MY_DIARY {
            override fun changeFragment(supportFragmentManager: FragmentManager) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.home_container, MyDiaryHomeFragment())
                    .commit()
            }
        },
        EXPLORE {
            override fun changeFragment(supportFragmentManager: FragmentManager) {
                // TODO
            }
        },
        COLLECTION {
            override fun changeFragment(supportFragmentManager: FragmentManager) {
                // TODO
            }
        }
        ;

        abstract fun changeFragment(supportFragmentManager: FragmentManager)

        companion object {
            fun from(title: String): BottomMenu {
                val toString = R.string.menu_myDiary.toString()
                return when (title) {
                    "내 일기" -> MY_DIARY
                    "둘러보기" -> EXPLORE
                    "보관함" -> COLLECTION
                    else -> {
                        println()
                        throw IllegalArgumentException("invalid menu item")
                    }
                }
            }
        }
    }
}