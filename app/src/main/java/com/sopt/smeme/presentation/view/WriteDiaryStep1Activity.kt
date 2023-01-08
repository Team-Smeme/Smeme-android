package com.sopt.smeme.presentation.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.sopt.smeme.R
import com.sopt.smeme.databinding.ActivityWriteStep1Binding

class WriteDiaryStep1Activity : AppCompatActivity() {
    private var _binding: ActivityWriteStep1Binding? = null
    private val binding: ActivityWriteStep1Binding
        get() = requireNotNull(_binding) { "error in WriteDiaryKoreanActivity" }

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

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_write_step1)
        binding.step1 = this

        setQuestionState()
        setColorTip()

        setOnClickCheckbox()
        toStep2()
        observeDiary()

    }

    private fun setColorTip() {
        val tipText: String = binding.txtTip.text.toString()
        val builder = SpannableStringBuilder(tipText)
        val colorPrimary = ForegroundColorSpan(Color.parseColor("#FE9870"))
        builder.setSpan(colorPrimary, 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.txtTip.text = builder
    }

    private fun setQuestionState() {
        val randomTopic: String = binding.txtRandomTopic.text.toString()
        val builder = SpannableStringBuilder(randomTopic)
        val fixFont = StyleSpan(R.style.TextAppearance_Body3)
        val colorPrimary = ForegroundColorSpan(Color.parseColor("#FE9870"))
        builder.setSpan(colorPrimary, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.setSpan(fixFont, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.txtRandomTopic.text = builder
    }

    private fun setOnClickCheckbox() {
        with(binding) {
            cbRandom.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    txtRandom.setTextColor(Color.parseColor("#FE9870"))
                    txtRandomTopic.visibility = View.GONE
                    btnRefresh.visibility = View.GONE
                } else {
                    txtRandom.setTextColor(Color.parseColor("#A6A6A6"))
                    txtRandomTopic.visibility = View.VISIBLE
                    btnRefresh.visibility = View.VISIBLE
                }
            }

            cbPublic.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    txtPublic.setTextColor(Color.parseColor("#FE9870"))
                } else {
                    txtPublic.setTextColor(Color.parseColor("#A6A6A6"))
                }
            }

            txtRandom.setOnClickListener {
                if (cbRandom.isChecked) {
                    txtRandom.setTextColor(Color.parseColor("#A6A6A6"))
                    cbRandom.isChecked = false
                    txtRandomTopic.visibility = View.GONE
                    btnRefresh.visibility = View.GONE
                } else {
                    txtRandom.setTextColor(Color.parseColor("#A6A6A6"))
                    cbRandom.isChecked = true
                    txtRandomTopic.visibility = View.VISIBLE
                    btnRefresh.visibility = View.VISIBLE
                }
            }


            txtPublic.setOnClickListener {
                if (cbPublic.isChecked) {
                    txtPublic.setTextColor(Color.parseColor("#A6A6A6"))
                    cbPublic.isChecked = false
                } else {
                    txtPublic.setTextColor(Color.parseColor("#A6A6A6"))
                    cbPublic.isChecked = true
                }
            }
        }
    }

    private fun toStep2() {
        binding.btnNext.setOnClickListener {
            val toStep2 = Intent(this, WriteDiaryStep2Activity::class.java)
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
