package com.sopt.smeme.presentation.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sopt.smeme.business.viewmodel.Step2ViewModel
import com.sopt.smeme.databinding.ActivityWriteStep2Binding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class WriteDiaryStep2Activity : AppCompatActivity() {
    private val vm: Step2ViewModel by viewModels()

    private var _binding: ActivityWriteStep2Binding? = null
    private val binding: ActivityWriteStep2Binding
        get() = requireNotNull(_binding) { "error in WriteDiaryKoreanActivity" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWriteStep2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etDiaryEnglish.requestFocus()

        val sourceDiary = intent.getStringExtra("source diary") ?: ""
        connectStep1(sourceDiary)

        showHint(sourceDiary)
//        var isRandomChecked = intent.getBooleanExtra("randomCheck", false)
//        var isPublicChecked = intent.getBooleanExtra("publicCheck", false)
//        with(binding) {
//            if (isRandomChecked) {
//                cbRandom.isChecked = true
//                cbRandom.isEnabled = false
//                txtRandom.setTextColor(Color.parseColor("#FE9870"))
//            } else {
//                cbRandom.isChecked = false
//                cbRandom.isEnabled = false
//                txtRandom.setTextColor(Color.parseColor("#A6A6A6"))
//            }
//
//            if (isPublicChecked) {
//                cbPublic.isChecked = true
//                cbRandom.isEnabled = false
//                txtPublic.setTextColor(Color.parseColor("#FE9870"))
//            } else {
//                cbPublic.isChecked = false
//                cbRandom.isEnabled = false
//                txtPublic.setTextColor(Color.parseColor("#A6A6A6"))
//            }
//        }

    }

    private fun connectStep1(source: String) {
        val toStep1 = Intent(this, WriteDiaryStep1Activity::class.java)

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
                toStep1.putExtra("diary", txtHint.text)
                setResult(RESULT_OK, toStep1)
                startActivity(toStep1)
            }
        }


    }

    private fun showHint(source: String) {
        binding.btnHint.setOnClickListener {
            vm.translate(
                source,
                onCompleted = {
                    Timber.d("")
                    binding.txtHint.text = it
                },
                onError = {
                    Timber.d(it.message)

                    runOnUiThread {
                        Toast.makeText(this, "번역과정중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                    binding.txtHint.text = source
                }
            )
        }
    }
}