package com.sopt.smeme.presentation.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sopt.smeme.Constants.Diary.Companion.DIARY
import com.sopt.smeme.business.viewmodel.mydiary.DiaryRegister
import com.sopt.smeme.business.viewmodel.mydiary.EnglishDiaryMoonJiGi
import com.sopt.smeme.business.viewmodel.mydiary.Topic
import com.sopt.smeme.databinding.ActivityWriteStep2Binding
import com.sopt.smeme.DiaryBooleanObserver
import com.sopt.smeme.presentation.Diary
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteDiaryStep2Activity : AppCompatActivity() {
    // TOOD : 다른 언어로 확장을 위해 추상화만 시켜둠
    private val diaryMoonJiGi: EnglishDiaryMoonJiGi by viewModels()
    private val diaryRegister: DiaryRegister by viewModels()

    private var _binding: ActivityWriteStep2Binding? = null
    private val binding: ActivityWriteStep2Binding
        get() = requireNotNull(_binding) { "error in WriteDiaryKoreanActivity" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWriteStep2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val diary = intent.getSerializableExtra(DIARY) as Diary

        constructLayout()
        setCheckBoxStateFromStep1(diary)
        observeDiary()
        showHint(diary)
        listen()
    }

    fun constructLayout() {
        binding.moonjigi = diaryMoonJiGi
        binding.lifecycleOwner = this
        binding.etDiaryEnglish.requestFocus()
    }


    private fun setCheckBoxStateFromStep1(diary: Diary) {
        binding.txtHint.text = diary.sourceDiaryContent
        binding.cbPublic.isChecked = diary.isPublic
        binding.cbRandom.isChecked = diary.isTopicSelected

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
            val diary = intent.getSerializableExtra(DIARY) as Diary
            val toString = binding.etDiaryEnglish.text.toString()
            diaryRegister.completeWriting(
                topicId = diary.topic.id,
                content = binding.etDiaryEnglish.text.toString(),
                languageCode = "en", // TODO
                isPublic = diary.isPublic,
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


    private fun showHint(diary: Diary) {
        binding.btnHint.setOnClickListener {
            if (binding.btnHint.isChecked) {
                binding.txtHint.text = diary.translatedText
            }
            // 번역을 해제하는 경우
            else {
                binding.txtHint.text = diary.sourceDiaryContent
            }

        }
    }

    private fun observeDiary() {
        diaryMoonJiGi.isDiarySuit.observe(this, DiaryBooleanObserver {
            diaryMoonJiGi.setCompleteState()
            if (diaryMoonJiGi.isCompleteActive.value == true) {
                binding.btnComplete.setTextColor(Color.parseColor("#171716"))
            } else {
                binding.btnComplete.setTextColor(Color.parseColor("#BBBBBB"))
            }
        })
    }

}