package com.sopt.smeme.presentation.view.home

import android.content.Context
import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sopt.smeme.R
import com.sopt.smeme.databinding.ActivityHomeBinding
import com.sopt.smeme.presentation.view.ViewBoundActivity
import com.sopt.smeme.presentation.view.archive.ArchiveFragment
import com.sopt.smeme.presentation.view.odir.OdirListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ViewBoundActivity<ActivityHomeBinding>(R.layout.activity_home) {

    override fun constructLayout() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.home_container)
        if (currentFragment == null && intent.getStringExtra("bnvMenu") != "둘러보기") {
            replaceFragment(MyDiaryHomeFragment(this))
        } else if (intent.getStringExtra("bnvMenu") == "둘러보기") {
            replaceFragment(OdirListFragment(this))
            binding.bnvMain.selectedItemId = R.id.ic_explore
            //TODO chip 색깔도 같이 바꾸기 finish하고 갱신이 더 좋은 방법인지..?
        }
    }

    override fun listen() {
        binding.bnvMain.setOnItemSelectedListener {
            BottomMenu.from(it.title.toString())
                .changeFragment(supportFragmentManager, this)
            return@setOnItemSelectedListener true
        }
    }

    fun changeBackgroundColor(color: String) {
        binding.bnvMain.setBackgroundColor(Color.parseColor(color))
    }

    private enum class BottomMenu {
        MY_DIARY {
            override fun changeFragment(
                supportFragmentManager: FragmentManager,
                context: Context,
            ) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.home_container, MyDiaryHomeFragment(context))
                    .commit()
            }
        },
        EXPLORE {
            override fun changeFragment(
                supportFragmentManager: FragmentManager,
                context: Context,
            ) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.home_container, OdirListFragment(context))
                    .commit()
            }
        },
        COLLECTION {
            override fun changeFragment(
                supportFragmentManager: FragmentManager,
                context: Context,
            ) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.home_container, ArchiveFragment(context))
                    .commit()
            }
        }
        ;

        abstract fun changeFragment(
            supportFragmentManager: FragmentManager,
            context: Context,
        )

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

    private fun replaceFragment(fragment: Fragment){
       supportFragmentManager.beginTransaction()
           .replace(R.id.home_container, fragment)
           .commit()
    }
}