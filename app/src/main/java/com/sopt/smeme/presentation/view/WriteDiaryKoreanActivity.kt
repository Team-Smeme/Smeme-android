package com.sopt.smeme.presentation.view

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
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

        setQuestionState()
        setColorTip()

        setOnClickCheckbox()

    }

    private fun setColorTip() {
        val tipText: String = binding.txtTip.text.toString()
        val builder = SpannableStringBuilder(tipText)
        val colorPrimary = ForegroundColorSpan(Color.parseColor("#FE9870"))
        builder.setSpan(colorPrimary, 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.txtTip.text = builder
    }

    private fun setQuestionState() {
        val randomTopic: String = binding.txtRandomTopic.text.toString()
        val builder = SpannableStringBuilder(randomTopic)
        val fixFont = StyleSpan(R.style.TextAppearance_Body3)
        val colorPrimary = ForegroundColorSpan(Color.parseColor("#FE9870"))
        builder.setSpan(colorPrimary, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.setSpan(fixFont, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.txtRandomTopic.text = builder
    }

    private fun setOnClickCheckbox() {
        with(binding) {
            cbRandom.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    txtRandom.setTextColor(Color.parseColor("#FE9870"))
                } else {
                    txtRandom.setTextColor(Color.parseColor("#A6A6A6"))
                }
            }

            cbPublic.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    txtPublic.setTextColor(Color.parseColor("#FE9870"))
                } else {
                    txtPublic.setTextColor(Color.parseColor("#A6A6A6"))
                }
            }

            txtRandom.setOnClickListener {
                if (cbRandom.isChecked) {
                    txtRandom.setTextColor(Color.parseColor("#A6A6A6"))
                    cbRandom.isChecked = false
                } else {
                    txtRandom.setTextColor(Color.parseColor("#A6A6A6"))
                    cbRandom.isChecked = true
                }
            }


            txtPublic.setOnClickListener {
                if (cbPublic.isChecked) {
                    txtPublic.setTextColor(Color.parseColor("#A6A6A6"))
                    cbPublic.isChecked = false
                } else {
                    txtPublic.setTextColor(Color.parseColor("#A6A6A6"))
                    cbPublic.isChecked = true
                }
            }
        }
    }
}