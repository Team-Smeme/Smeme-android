package com.sopt.smeme.presentation.view.mdir

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.BidiFormatter
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.sopt.smeme.Constants.Diary.Companion.DIARY
import com.sopt.smeme.DiaryBooleanObserver
import com.sopt.smeme.R
import com.sopt.smeme.business.viewmodel.Translator
import com.sopt.smeme.business.viewmodel.mydiary.SourceDiaryMoonJiGi
import com.sopt.smeme.business.viewmodel.mydiary.Topic
import com.sopt.smeme.business.viewmodel.mydiary.TopicProvider
import com.sopt.smeme.databinding.ActivityWriteStep1Binding
import com.sopt.smeme.presentation.Diary
import com.sopt.smeme.presentation.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class WriteDiaryStep1Activity : AppCompatActivity() {
    private val translator: Translator by viewModels()
    private val topicProvider: TopicProvider by viewModels()
    private val sourceDiaryObserver: SourceDiaryMoonJiGi by viewModels()

    private var _binding: ActivityWriteStep1Binding? = null
    private val binding: ActivityWriteStep1Binding
        get() = requireNotNull(_binding) { "error in WriteDiaryKoreanActivity" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWriteStep1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        constructLayout()
        toStep2()
        listen()
        observe()
    }

    fun constructLayout() {
        binding.step1 = sourceDiaryObserver
        binding.lifecycleOwner = this
        binding.etDiaryKorean.requestFocus()

        setQuestionState("")
        setColorTip()
    }

    private fun setColorTip() {
        val tipText: String = binding.txtTip.text.toString()
        val builder = SpannableStringBuilder(tipText)
        val colorPrimary = ForegroundColorSpan(Color.parseColor("#FE9870"))
        builder.setSpan(colorPrimary, 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.txtTip.text = builder
    }

    private fun setQuestionState(text: String) {
        val randomTopic = "Q. $text"
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
            // check ?????? ??????
            cbRandom.setOnCheckedChangeListener { _, isChecked ->
                // check ?????? ??????
                if (isChecked) {
                    txtRandom.setTextColor(Color.parseColor("#FE9870"))
                    txtRandomTopic.visibility = View.VISIBLE
                    btnRefresh.visibility = View.VISIBLE

                    toStep2.putExtra("randomCheck", isChecked)
                    Timber.d("isRandomChecked $isChecked")

                    topicProvider.getRandomTopic {
                        runOnUiThread {
                            Timber.e(it.message)
                            Toast.makeText(
                                applicationContext,
                                "?????? ????????? ???????????? ???????????????. ????????? ????????????",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                // check ???????????? ??????
                else {
                    topicProvider.clear()
                    txtRandom.setTextColor(Color.parseColor("#A6A6A6"))
                    txtRandomTopic.visibility = View.GONE
                    btnRefresh.visibility = View.GONE

                }
            }

            layoutRandomTopic.setOnClickListener {
                // check ??? ????????? ??????
                if (!cbRandom.isChecked) {
                    cbRandom.isChecked = true
                    txtRandom.setTextColor(Color.parseColor("#FE9870"))
                    txtRandomTopic.visibility = View.VISIBLE
                    btnRefresh.visibility = View.VISIBLE
                }

                // check ??? ?????? ?????? ??????
                else {
                    topicProvider.clear()
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
                // public check ?????? ??????
                if (!cbPublic.isChecked) {
                    cbPublic.isChecked = true
                    txtPublic.setTextColor(Color.parseColor("#FE9870"))
                }

                // public check ???????????? ??????
                else {
                    cbPublic.isChecked = false
                    txtPublic.setTextColor(Color.parseColor("#A6A6A6"))
                }
            }
        }
    }

    fun observe() {
        topicProvider.topic.observe(this) {
            setQuestionState(it.text)
        }
        observeDiary()
    }

    fun listen() {
        binding.btnRefresh.setOnClickListener {
            topicProvider.getRandomTopic {
                runOnUiThread {
                    Timber.e(it.message)
                    Toast.makeText(
                        this,
                        "?????? ????????? ???????????? ???????????????. ????????? ????????????",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        binding.txtCancel.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }

        setOnClickCheckbox()
    }

    private fun toStep2() {
        val toStep2 = Intent(this, WriteDiaryStep2Activity::class.java)
        binding.btnNext.setOnClickListener {
            translator.translate(
                text = binding.etDiaryKorean.text.toString(),
                sourceCode = "ko",
                targetCode = "en",
                onCompleted = {
                    toStep2.putExtra(
                        DIARY, Diary(
                            topic = topicProvider.topic.value ?: Topic("", 0),
                            isPublic = binding.cbPublic.isChecked,
                            sourceDiaryContent = binding.etDiaryKorean.text.toString(),
                            isTopicSelected = binding.cbRandom.isChecked,
                            translatedText = it
                        )
                    )
                    startActivity(toStep2)
                }) {
                runOnUiThread {
                    Toast.makeText(this, "??????????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeDiary() {
        sourceDiaryObserver.isDiarySuit.observe(this, DiaryBooleanObserver {
            sourceDiaryObserver.setNextState()
            if (sourceDiaryObserver.isNextActive.value == true) {
                binding.btnNext.setTextColor(Color.parseColor("#171716"))
            } else {
                binding.btnNext.setTextColor(Color.parseColor("#BBBBBB"))
                binding.btnNext.isEnabled = false
            }
        })
    }

}