package com.sopt.smeme.presentation.view.mdir

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.sopt.smeme.Constants
import com.sopt.smeme.business.viewmodel.mydiary.DiaryRegister
import com.sopt.smeme.business.viewmodel.mydiary.EnglishDiaryMoonJiGi
import com.sopt.smeme.databinding.ActivityWriteStep2Binding
import com.sopt.smeme.presentation.Diary
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

        val diary = intent.getSerializableExtra(Constants.Diary.DIARY) as Diary

        constructLayout()
        setCheckBoxStateFromStep1(diary)
        observeDiary()
        showHint(diary)
        listen()
        setOnClickCheckBox()
    }

    fun constructLayout() {
        binding.moonjigi = diaryValidation
        binding.lifecycleOwner = this
        binding.etDiaryEnglish.requestFocus()
    }


    private fun setCheckBoxStateFromStep1(diary: Diary) {
        binding.txtHint.text = diary.sourceDiaryContent
        binding.cbPublic.isChecked = diary.isPublic
        binding.cbRandom.isChecked = diary.isTopicSelected

        if (diary.isTopicSelected) {
            binding.txtRandom.setTextColor(Color.parseColor("#FE9870"))
        } else {
            binding.txtRandom.setTextColor(Color.parseColor("#A6A6A6"))
        }

        if (diary.isPublic) {
            binding.txtPublic.setTextColor(Color.parseColor("#FE9870"))
        } else {
            binding.txtPublic.setTextColor(Color.parseColor("#A6A6A6"))
        }
        with(binding) {
            btnBack.setOnClickListener {
                finish()
            }
        }
    }

    fun listen() {
        binding.btnComplete.setOnClickListener {
            val diary = intent.getSerializableExtra(Constants.Diary.DIARY) as Diary
            diaryRegister.completeWriting(
                topicId = diary.topic.id,
                content = binding.etDiaryEnglish.text.toString(),
                languageCode = "en", // TODO
                isPublic = binding.cbPublic.isChecked,
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
        diaryValidation.isDiarySuit.observe(this) {
            diaryValidation.setCompleteState()
            if (diaryValidation.isCompleteActive.value == true) {
                binding.btnComplete.setTextColor(Color.parseColor("#171716"))
            } else {
                binding.btnComplete.setTextColor(Color.parseColor("#BBBBBB"))
                binding.btnComplete.isEnabled = false
            }
        }
    }

    private fun setOnClickCheckBox(){
        with(binding){
            layoutPublic.setOnClickListener {
                // public check 하는 경우
                if (!cbPublic.isChecked) {
                    cbPublic.isChecked = true
                    txtPublic.setTextColor(Color.parseColor("#FE9870"))
                }

                // public check 해제하는 경우
                else {
                    cbPublic.isChecked = false
                    txtPublic.setTextColor(Color.parseColor("#A6A6A6"))
                }
            }
        }
    }

}