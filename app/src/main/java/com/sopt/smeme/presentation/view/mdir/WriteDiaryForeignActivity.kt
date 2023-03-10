package com.sopt.smeme.presentation.view.mdir

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
import androidx.lifecycle.observe
import com.sopt.smeme.Constants.Diary.Companion.DIARY
import com.sopt.smeme.DiaryBooleanObserver
import com.sopt.smeme.R
import com.sopt.smeme.business.viewmodel.ForeignViewModel
import com.sopt.smeme.business.viewmodel.mydiary.DiaryRegister
import com.sopt.smeme.business.viewmodel.mydiary.Topic
import com.sopt.smeme.business.viewmodel.mydiary.TopicProvider
import com.sopt.smeme.databinding.ActivityWriteForeignBinding
import com.sopt.smeme.presentation.Diary
import com.sopt.smeme.presentation.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class WriteDiaryForeignActivity : AppCompatActivity() {
    private val foreignViewModel by viewModels<ForeignViewModel>()
    private val diaryRegister: DiaryRegister by viewModels()
    private val topicProvider: TopicProvider by viewModels()

    private var _binding: ActivityWriteForeignBinding? = null
    private val binding: ActivityWriteForeignBinding
        get() = requireNotNull(_binding) { "error in WriteDiaryForeignActivity" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWriteForeignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etDiaryEnglish.requestFocus()

        binding.foreign = foreignViewModel
        binding.lifecycleOwner = this

        setOnClickCheckbox()
        observeDiary()
        listen()
        toHome()
        observe()
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
        with(binding) {
            cbRandom.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
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

                    txtRandom.setTextColor(Color.parseColor("#FE9870"))
                    txtRandomTopic.visibility = View.VISIBLE
                    btnRefresh.visibility = View.VISIBLE
                } else {
                    txtRandom.setTextColor(Color.parseColor("#A6A6A6"))
                    txtRandomTopic.visibility = View.GONE
                    btnRefresh.visibility = View.GONE
                    topicProvider.clear()
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
                cbRandom.isChecked = !cbRandom.isChecked
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

    private fun observeDiary() {
        foreignViewModel.isDiarySuit.observe(this, DiaryBooleanObserver {
            foreignViewModel.setCompleteState()
            if (foreignViewModel.isCompleteActive.value == true) {
                binding.btnComplete.setTextColor(Color.parseColor("#171716"))
            } else {
                binding.btnComplete.setTextColor(Color.parseColor("#BBBBBB"))
                binding.btnComplete.isEnabled = false
            }
        })
    }

    private fun listen() {
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

        // TODO : ?????? ????????????
        binding.btnComplete.setOnClickListener {
            diaryRegister.completeWriting(
                topicId = topicProvider.topic.value?.id ?: 0,
                content = binding.etDiaryEnglish.text.toString(),
                languageCode = "en", // TODO
                isPublic = binding.cbPublic.isChecked,
                onCompleted = {
                    navigateToDetail(it)
                },
                onError = {
                    runOnUiThread {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }

    private fun navigateToDetail(id: Long) {
        val intent = Intent(this, MdirDetailActivity::class.java).apply {
            putExtra("diaryId", id)
        }
        startActivity(intent)
        finish()
    }

    private fun toHome() {
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
    }

    private fun observe() {
        topicProvider.topic.observe(this) {
            setQuestionState(it.text)
        }
    }
}