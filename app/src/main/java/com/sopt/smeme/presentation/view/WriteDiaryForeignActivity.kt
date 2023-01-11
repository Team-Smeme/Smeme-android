package com.sopt.smeme.presentation.view

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.sopt.smeme.R
import com.sopt.smeme.business.viewmodel.ForeignViewModel
import com.sopt.smeme.databinding.ActivityWriteForeignBinding

class WriteDiaryForeignActivity : AppCompatActivity() {
    private val foreignViewModel by viewModels<ForeignViewModel>()

    private var _binding: ActivityWriteForeignBinding? = null
    private val binding: ActivityWriteForeignBinding
        get() = requireNotNull(_binding) { "error in WriteDiaryForeignActivity" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWriteForeignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etDiaryEnglish.requestFocus()

        binding.foreign = foreignViewModel
        binding.lifecycleOwner = this

        setQuestionState()
        setOnClickCheckbox()
        observeDiary()
        toHome()
        writeComplete()
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

    private fun observeDiary() {
        foreignViewModel.isDiarySuit.observe(this) {
            foreignViewModel.setCompleteState()
            if (foreignViewModel.isCompleteActive.value == true) {
                binding.btnComplete.setTextColor(Color.parseColor("#171716"))
            } else {
                binding.btnComplete.setTextColor(Color.parseColor("#BBBBBB"))
            }
        }
    }

    private fun toHome() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun writeComplete() {
        binding.btnComplete.setOnClickListener {
            finish()
        }
    }

}