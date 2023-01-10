package com.sopt.smeme.presentation.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sopt.smeme.business.viewmodel.Step2ViewModel
import com.sopt.smeme.databinding.ActivityWriteStep2Binding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

class WriteDiaryStep2Activity : AppCompatActivity() {
    private var _binding: ActivityWriteStep2Binding? = null
    private val binding: ActivityWriteStep2Binding
        get() = requireNotNull(_binding) { "error in WriteDiaryKoreanActivity" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWriteStep2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etDiaryEnglish.requestFocus()

//        val fromStep1 = intent.getStringExtra("번역 결과")
        val translatedDiary = intent.getStringExtra("translated text")
        val sourceDiary = intent.getStringExtra("source diary") ?: ""
        Timber.d("!?!?!?액티비티에서의 결과 $sourceDiary")
        Timber.d("!?!?!?액티비티에서의 결과 $translatedDiary")
//        Timber.d("<>>>>>>>>>액티비티에서의 결과 $fromStep1")


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
        val translatedDiary = intent.getStringExtra("translated text")
        binding.btnHint.setOnClickListener {
            // 번역으로 바뀐 경우
            Timber.d("translatedDiary:$translatedDiary")
            if (binding.btnHint.isChecked) {
                binding.txtHint.text = translatedDiary
//                vm.translate(
//                    source,
//                    onCompleted = {
//                        Timber.d("")
//                        binding.txtHint.text = it
//                    },
//                    onError = {
//                        Timber.d(it.message)
//
//
//                        binding.txtHint.text = source
//                    }
//                )
            }

            // 번역을 해제하는 경우
            else {
                binding.txtHint.text = source
            }

        }
    }
}