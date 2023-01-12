package com.sopt.smeme.presentation.view

import android.content.Context
import android.graphics.Color
import androidx.fragment.app.FragmentManager
import com.sopt.smeme.R
import com.sopt.smeme.databinding.ActivityHomeBinding
import com.sopt.smeme.presentation.view.archive.ArchiveFragment
import com.sopt.smeme.presentation.view.odir.OdirListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ViewBoundActivity<ActivityHomeBinding>(R.layout.activity_home) {

    override fun constructLayout() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.home_container)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.home_container, MyDiaryHomeFragment(this))
                .commit()
        }
    }

    override fun listen() {
        binding.bnvMain.setOnItemSelectedListener {
            BottomMenu
                .from(it.title.toString())
                .changeFragment(supportFragmentManager, this)
            return@setOnItemSelectedListener true
        }
    }

    fun changeBackgroundColor(color: String) {
        binding.bnvMain.setBackgroundColor(Color.parseColor(color))
    }

    private enum class BottomMenu {
        MY_DIARY {
            override fun changeFragment(supportFragmentManager: FragmentManager, context: Context) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.home_container, MyDiaryHomeFragment(context))
                    .commit()
            }
        },
        EXPLORE {
            override fun changeFragment(supportFragmentManager: FragmentManager, context: Context) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.home_container, OdirListFragment(context))
                    .commit()
            }
        },
        COLLECTION {
            override fun changeFragment(supportFragmentManager: FragmentManager, context: Context) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.home_container, ArchiveFragment(context))
                    .commit()
            }
        }
        ;

        abstract fun changeFragment(supportFragmentManager: FragmentManager, context: Context)

        companion object {
            fun from(title: String): BottomMenu {
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