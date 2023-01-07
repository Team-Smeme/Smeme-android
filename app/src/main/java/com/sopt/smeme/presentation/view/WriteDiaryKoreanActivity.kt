package com.sopt.smeme.presentation.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import com.sopt.smeme.R
import com.sopt.smeme.databinding.ActivityWrteDiaryKoreanBinding

class WriteDiaryKoreanActivity : AppCompatActivity() {
    private var _binding: ActivityWrteDiaryKoreanBinding? = null
    private val binding: ActivityWrteDiaryKoreanBinding
        get() = requireNotNull(_binding) { "error in WriteDiaryKoreanActivity" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWrteDiaryKoreanBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setColorTip()
    }

    private fun setColorTip() {
        val tipText: String = binding.txtTip.text.toString()
        val builder = SpannableStringBuilder(tipText)
        val colorPrimary = ForegroundColorSpan(Color.parseColor("#FE9870"))
        builder.setSpan(colorPrimary, 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.txtTip.text = builder
    }

}