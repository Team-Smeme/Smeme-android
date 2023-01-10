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
import androidx.lifecycle.*
import com.sopt.smeme.R
import com.sopt.smeme.business.viewmodel.mydiary.DiarySource2TargetManager
import com.sopt.smeme.business.viewmodel.mydiary.Topic
import com.sopt.smeme.business.viewmodel.Step1ViewModel
import com.sopt.smeme.databinding.ActivityWriteStep1Binding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteDiaryStep1Activity : AppCompatActivity() {
    private val vm : Step1ViewModel by viewModels()

    private var _binding: ActivityWriteStep1Binding? = null
    private val binding: ActivityWriteStep1Binding
        get() = requireNotNull(_binding) { "error in WriteDiaryKoreanActivity" }
    private val diarySource2TargetManager: DiarySource2TargetManager by viewModels()

    private lateinit var sourceDiary: String

//    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWriteStep1Binding.inflate(layoutInflater)
        setContentView(binding.root)
//        _binding = DataBindingUtil.setContentView(this, R.layout.activity_write_step1)

        binding.step1 = vm
        binding.lifecycleOwner = this
        binding.etDiaryKorean.requestFocus()

        setQuestionState()
        setColorTip()
        setOnClickCheckbox()
        observeDiary()
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
        val toStep2 = Intent(this, WriteDiaryStep2Activity::class.java)
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
                    toStep2.putExtra("randomCheck", isChecked)
                    Timber.d("isRandomChecked $isChecked")
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
                    toStep2.putExtra("publicCheck", isChecked)
                    Timber.d("isPublicChecked $isChecked")
                } else {
                    txtPublic.setTextColor(Color.parseColor("#A6A6A6"))
                    Timber.d("isPublicChecked $isChecked")
                    toStep2.putExtra("publicCheck", isChecked)
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

    private fun toStep2() {
        val toStep2 = Intent(this, WriteDiaryStep2Activity::class.java)
        var translatedDiary:String? = null
        binding.btnNext.setOnClickListener {
            vm.updateText(binding.etDiaryKorean.text.toString())
            vm.content.observe(this, Observer<String> { sourceDiary = it })
            toStep2.putExtra("source diary", sourceDiary)

            vm.translate(sourceDiary, onCompleted = {
                Timber.d("액티비티에서의 결과 $it")
                toStep2.putExtra("translated text",it)
                translatedDiary = it
            }
            ) {
                runOnUiThread {
                    Toast.makeText(this, "번역과정중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            toStep2.putExtra("번역 결과",translatedDiary)
            Timber.d("스텝원 번역: $translatedDiary")
            startActivity(toStep2)
        }
    }

    private fun observeDiary() {
        vm.isDiarySuit.observe(this) {
            vm.setNextState()
            if(vm.isNextActive.value == true){
                binding.btnNext.setTextColor(Color.parseColor("#171716"))
            }
            else{
                binding.btnNext.setTextColor(Color.parseColor("#BBBBBB"))
            }
        }
    }
//
//    companion object{
//        const val REQ_CODE_DIARY = 1000
//    }
}