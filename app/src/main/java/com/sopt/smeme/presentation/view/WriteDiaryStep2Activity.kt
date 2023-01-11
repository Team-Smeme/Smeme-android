package com.sopt.smeme.presentation.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.sopt.smeme.business.viewmodel.Step1ViewModel
import com.sopt.smeme.business.viewmodel.Step2ViewModel
import com.sopt.smeme.databinding.ActivityWriteStep2Binding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class WriteDiaryStep2Activity : AppCompatActivity() {
    private val step2 by viewModels<Step2ViewModel>()

    private var _binding: ActivityWriteStep2Binding? = null
    private val binding: ActivityWriteStep2Binding
        get() = requireNotNull(_binding) { "error in WriteDiaryKoreanActivity" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWriteStep2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.step2 = step2
        binding.lifecycleOwner = this
        binding.etDiaryEnglish.requestFocus()

        val sourceDiary = intent.getStringExtra("source diary") ?: ""

        connectStep1(sourceDiary)
        observeDiary()
        showHint(sourceDiary)
    }


    private fun connectStep1(source: String) {
        binding.txtHint.text = source
        binding.cbRandom.isChecked = intent.getBooleanExtra("randomCheck", false)
        binding.cbRandom.isChecked = intent.getBooleanExtra("publicCheck", false)

        with(binding) {
            cbRandom.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    cbRandom.isEnabled = false
                    txtRandom.setTextColor(Color.parseColor("#FE9870"))
                } else {
                    cbRandom.isEnabled = false
                    txtRandom.setTextColor(Color.parseColor("#A6A6A6"))
                }
            }

            cbPublic.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    cbRandom.isEnabled = false
                    txtPublic.setTextColor(Color.parseColor("#FE9870"))
                } else {
                    cbRandom.isEnabled = false
                    txtPublic.setTextColor(Color.parseColor("#A6A6A6"))
                }
            }
            btnBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun showHint(source: String) {
        val translatedDiary = intent.getStringExtra("translated text")
        binding.btnHint.setOnClickListener {
            // 번역으로 바뀐 경우
            Timber.d("translatedDiary:$translatedDiary")
            if (binding.btnHint.isChecked) {
                binding.txtHint.text = translatedDiary
            }
            // 번역을 해제하는 경우
            else {
                binding.txtHint.text = source
            }

        }
    }

    private fun observeDiary() {
        step2.isDiarySuit.observe(this) {
            step2.setCompleteState()
            if (step2.isCompleteActive.value == true) {
                binding.btnComplete.setTextColor(Color.parseColor("#171716"))
            } else {
                binding.btnComplete.setTextColor(Color.parseColor("#BBBBBB"))
            }
        }
    }

}