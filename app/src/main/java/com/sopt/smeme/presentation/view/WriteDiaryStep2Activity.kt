package com.sopt.smeme.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sopt.smeme.databinding.ActivityWriteKoreanStep2Binding

class WriteDiaryStep2Activity : AppCompatActivity() {
    private var _binding: ActivityWriteKoreanStep2Binding? = null
    private val binding: ActivityWriteKoreanStep2Binding
        get() = requireNotNull(_binding) { "error in WriteDiaryKoreanActivity" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWriteKoreanStep2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}