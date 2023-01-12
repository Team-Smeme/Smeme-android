package com.sopt.smeme.presentation.view.mdir

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.sopt.smeme.business.viewmodel.mydiary.DiaryRegister
import com.sopt.smeme.business.viewmodel.mydiary.EnglishDiaryMoonJiGi
import com.sopt.smeme.business.viewmodel.mydiary.Topic
import com.sopt.smeme.databinding.ActivityWriteStep2Binding
import com.sopt.smeme.presentation.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteDiaryStep2Activity : AppCompatActivity() {
    // TOOD : 다른 언어로 확장을 위해 추상화만 시켜둠
    private val diaryValidation: EnglishDiaryMoonJiGi by viewModels()
    private val diaryRegister: DiaryRegister by viewModels()

    private var _binding: ActivityWriteStep2Binding? = null
    private val binding: ActivityWriteStep2Binding
        get() = requireNotNull(_binding) { "error in WriteDiaryKoreanActivity" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWriteStep2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val sourceDiary = intent.getStringExtra("source diary") ?: ""

        constructLayout()
        setCheckBoxStateFromStep1(sourceDiary)
        observeDiary()
        showHint(sourceDiary)
        listen()
    }

    fun constructLayout() {
        binding.moonjigi = diaryValidation
        binding.lifecycleOwner = this
        binding.etDiaryEnglish.requestFocus()
    }


    private fun setCheckBoxStateFromStep1(source: String) {
        binding.txtHint.text = source
        binding.cbRandom.isChecked = intent.getBooleanExtra("randomCheck", false)
        binding.cbRandom.isChecked = intent.getBooleanExtra("publicCheck", false)

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
            btnBack.setOnClickListener {
                finish()
            }
        }
    }

    fun listen() {
        binding.btnComplete.setOnClickListener {
            val topic = intent.getSerializableExtra("topic") as Topic
            val isPublic = intent.getBooleanExtra("isPublic", true)

            diaryRegister.completeWriting(
                topicId = topic.id,
                content = binding.etDiaryEnglish.text.toString(),
                languageCode = "en", // TODO
                isPublic = isPublic,
                onCompleted = {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                },
                onError = {
                    runOnUiThread {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }


    private fun showHint(source: String) {
        val translatedDiary = intent.getStringExtra("translated text")
        binding.btnHint.setOnClickListener {
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
        diaryValidation.isDiarySuit.observe(this) {
            diaryValidation.setCompleteState()
            if (diaryValidation.isCompleteActive.value == true) {
                binding.btnComplete.setTextColor(Color.parseColor("#171716"))
            } else {
                binding.btnComplete.setTextColor(Color.parseColor("#BBBBBB"))
            }
        }
    }

}