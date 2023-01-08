package com.sopt.smeme.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sopt.smeme.databinding.ActivityWriteStep2Binding

class WriteDiaryStep2Activity : AppCompatActivity() {
    private var _binding: ActivityWriteStep2Binding? = null
    private val binding: ActivityWriteStep2Binding
        get() = requireNotNull(_binding) { "error in WriteDiaryKoreanActivity" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWriteStep2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        toStep1()
    }

    private fun toStep1() {
        binding.btnBack.setOnClickListener {
            val toStep1 = Intent(this, WriteDiaryStep1Activity::class.java)
            startActivity(toStep1)
        }
    }
}