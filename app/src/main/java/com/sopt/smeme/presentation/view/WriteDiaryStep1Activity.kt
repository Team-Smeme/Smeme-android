package com.sopt.smeme.presentation.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sopt.smeme.R
import com.sopt.smeme.databinding.ActivityWriteStep1Binding

class WriteDiaryStep1Activity : AppCompatActivity() {
    private var _binding: ActivityWriteStep1Binding? = null
    private val binding: ActivityWriteStep1Binding
        get() = requireNotNull(_binding) { "error in WriteDiaryKoreanActivity" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWriteStep1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setQuestionState()
        setColorTip()

        setOnClickCheckbox()
        toStep2()

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
                    txtRandomTopic.visibility = View.GONE
                    btnRefresh.visibility = View.GONE
                } else {
                    txtRandom.setTextColor(Color.parseColor("#A6A6A6"))
                    txtRandomTopic.visibility = View.VISIBLE
                    btnRefresh.visibility = View.VISIBLE
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
                    txtRandomTopic.visibility = View.GONE
                    btnRefresh.visibility = View.GONE
                } else {
                    txtRandom.setTextColor(Color.parseColor("#A6A6A6"))
                    cbRandom.isChecked = true
                    txtRandomTopic.visibility = View.VISIBLE
                    btnRefresh.visibility = View.VISIBLE
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

    private fun toStep2() {
        binding.txtNext.setOnClickListener {
            val toStep2 = Intent(this, WriteDiaryStep2Activity::class.java)
            startActivity(toStep2)
        }

    }

    private fun checkDiaryCondition() {
        with(binding) {
            if (etDiaryKorean.text.trim().toString().length >= 10) {
                txtNext.isEnabled = true
                txtNext.setTextColor(Color.parseColor("#171716"))
            }
        }
    }
}
