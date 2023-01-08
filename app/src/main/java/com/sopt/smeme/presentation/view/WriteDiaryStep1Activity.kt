package com.sopt.smeme.presentation.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.sopt.smeme.R
import com.sopt.smeme.business.viewmodel.mydiary.DiarySource2TargetManager
import com.sopt.smeme.business.viewmodel.mydiary.Topic
import com.sopt.smeme.databinding.ActivityWriteStep1Binding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class WriteDiaryStep1Activity : AppCompatActivity() {
    private var _binding: ActivityWriteStep1Binding? = null
    private val binding: ActivityWriteStep1Binding
        get() = requireNotNull(_binding) { "error in WriteDiaryKoreanActivity" }
    private val diarySource2TargetManager: DiarySource2TargetManager by viewModels()

//    private val _signUpResult = MutableLiveData<String>()
//    val signUpResult: LiveData<String>
//        get() = _signUpResult

//    private val _errorMessage = MutableLiveData<String>()
//    val errorMessage: LiveData<String>
//        get() = _errorMessage

    val diary: MutableLiveData<String> = MutableLiveData("")
    val isDiarySuit: LiveData<Boolean> = Transformations.map(diary) { isValidDiaryFormat(it) }

    private var _isNextActive = MutableLiveData(false)
    val isNextActive get() = _isNextActive

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWriteStep1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setQuestionState()
        setColorTip()

        setOnClickCheckbox()
        toStep2()
        listen()
        observe()
        observeDiary()
    }

    private fun setColorTip() {
        val tipText: String = binding.txtTip.text.toString()
        val builder = SpannableStringBuilder(tipText)
        val colorPrimary = ForegroundColorSpan(Color.parseColor("#FE9870"))
        builder.setSpan(colorPrimary, 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.txtTip.text = builder
    }

    private fun setQuestionState(text: String) {
        val randomTopic = "Q. ${text}"
        val builder = SpannableStringBuilder(randomTopic)
        val fixFont = StyleSpan(R.style.TextAppearance_Body3)
        val colorPrimary = ForegroundColorSpan(Color.parseColor("#FE9870"))
        builder.setSpan(colorPrimary, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.setSpan(fixFont, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.txtRandomTopic.text = builder
    }

    private fun setOnClickCheckbox() {
        with(binding) {
            // random //
            // check 되는 경우
            cbRandom.setOnCheckedChangeListener { _, isChecked ->
                // check 하는 경우
                if (isChecked) {
                    txtRandom.setTextColor(Color.parseColor("#FE9870"))
                    txtRandomTopic.visibility = View.VISIBLE
                    btnRefresh.visibility = View.VISIBLE

                    diarySource2TargetManager.getRandomTopic {
                        runOnUiThread {
                            Timber.e(it.message)
                            Toast.makeText(
                                applicationContext,
                                "랜덤 주제를 불러오지 못했습니다. 재시도 해주세요",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                // check 취소하는 경우
                else {
                    txtRandom.setTextColor(Color.parseColor("#A6A6A6"))
                    txtRandomTopic.visibility = View.GONE
                    btnRefresh.visibility = View.GONE
                }
            }

            layoutRandomTopic.setOnClickListener {
                // check 를 선택한 경우
                if (!cbRandom.isChecked) {
                    cbRandom.isChecked = true
                    txtRandom.setTextColor(Color.parseColor("#FE9870"))
                    txtRandomTopic.visibility = View.VISIBLE
                    btnRefresh.visibility = View.VISIBLE
                }

                // check 를 해제 하는 경우
                else {
                    cbRandom.isChecked = false
                    txtRandom.setTextColor(Color.parseColor("#A6A6A6"))
                    txtRandomTopic.visibility = View.GONE
                    btnRefresh.visibility = View.GONE

                }
            }

            // public //
            cbPublic.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    txtPublic.setTextColor(Color.parseColor("#FE9870"))
                } else {
                    txtPublic.setTextColor(Color.parseColor("#A6A6A6"))
                }
            }
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

    fun observe() {
        diarySource2TargetManager.topic.observe(this) {
            setQuestionState(it.text)
        }
    }

    fun listen() {
        binding.btnRefresh.setOnClickListener {
            diarySource2TargetManager.getRandomTopic {
                runOnUiThread {
                    Timber.e(it.message)
                    Toast.makeText(
                        this,
                        "랜덤 주제를 불러오지 못했습니다. 재시도 해주세요",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun toStep2() {
        binding.btnNext.setOnClickListener {
            val toStep2 = Intent(this, WriteDiaryStep2Activity::class.java)
            val topic = diarySource2TargetManager.topic.value
            val isPublic = binding.cbPublic.isChecked

            if (topic != null) {
                toStep2.putExtra("topic", topic)
            } else {
                toStep2.putExtra("topic", Topic("", 0))
            }
            toStep2.putExtra("isPublic", isPublic)
            startActivity(toStep2)
        }

    }

    private fun isValidDiaryFormat(diary: String) = diary.trim().length >= 10

    private fun setNextState() {
        if(isDiarySuit.value == true){
            _isNextActive.value = true
            binding.btnNext.isEnabled = true
            binding.btnNext.setTextColor(Color.parseColor("#171716"))
        }
        else{
            binding.btnNext.isEnabled = false
            binding.btnNext.setTextColor(Color.parseColor("#BBBBBB"))
        }
    }

    private fun observeDiary() {
        isDiarySuit.observe(this) {
            setNextState()
        }
    }
}
