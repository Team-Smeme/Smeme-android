package com.sopt.smeme.presentation.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sopt.smeme.business.viewmodel.mydiary.DiarySource2TargetManager
import com.sopt.smeme.business.viewmodel.mydiary.Topic
import com.sopt.smeme.databinding.ActivityWriteKoreanStep2Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteDiaryStep2Activity : AppCompatActivity() {
    private var _binding: ActivityWriteKoreanStep2Binding? = null
    private val binding: ActivityWriteKoreanStep2Binding
        get() = requireNotNull(_binding) { "error in WriteDiaryKoreanActivity" }
    private val diarySource2TargetManager: DiarySource2TargetManager by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWriteKoreanStep2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val serializableExtra = intent.getSerializableExtra("topic")
        val booleanExtra = intent.getBooleanExtra("isPublic", false)
        toStep1()
        listen()
    }

    private fun toStep1() {
        binding.btnBack.setOnClickListener {
            val toStep1 = Intent(this, WriteDiaryStep1Activity::class.java)
            startActivity(toStep1)
        }
    }

    fun listen() {
        binding.btnKoreanComplete.setOnClickListener {
            val topic = intent.getSerializableExtra("topic") as Topic
            val isPublic = intent.getBooleanExtra("isPublic", true)

            diarySource2TargetManager.completeWriting(
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
    }


}