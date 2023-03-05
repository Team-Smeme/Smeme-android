package com.sopt.smeme.presentation.view.odir

import android.R.id
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.sopt.smeme.DateUtil
import com.sopt.smeme.R
import com.sopt.smeme.business.viewmodel.Translator
import com.sopt.smeme.business.viewmodel.opendiary.OdirDetailProvider
import com.sopt.smeme.business.viewmodel.opendiary.OdirLikeProvider
import com.sopt.smeme.business.viewmodel.opendiary.OdirScrapProvider
import com.sopt.smeme.databinding.ActivityOdirDetailBinding
import com.sopt.smeme.presentation.view.ViewBoundActivity
import com.sopt.smeme.presentation.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OdirDetailActivity :
    ViewBoundActivity<ActivityOdirDetailBinding>(R.layout.activity_odir_detail) {

    private val odirDetailProvider: OdirDetailProvider by viewModels()
    private val odirScrapProvider: OdirScrapProvider by viewModels()
    private val odirLikeProvider: OdirLikeProvider by viewModels()
    private var likeNum: Int = 0
    private var sourceDiary: String = ""

    private val translator: Translator by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        constructLayout()
        observe()
        listen()
    }

    override fun constructLayout() {
        val diaryId = intent.getIntExtra("diaryId", -1)

        binding.tvDiaryOdirDetail.customSelectionActionModeCallback = actionModeCallback

        odirDetailProvider.requestGetDiary(
            diaryId,
            onError = {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        )
    }

    override fun listen() {
        val diaryId = intent.getIntExtra("diaryId", -1)

        binding.btnBackOdirDetail.setOnClickListener {

            finish()

        }

        binding.btnHintOdirDetail.setOnClickListener {
            if (binding.btnHintOdirDetail.isChecked) {
                translator.translate(
                    text = binding.tvDiaryOdirDetail.text.toString(),
                    sourceCode = "en",
                    targetCode = "ko",
                    onCompleted = {
                        binding.tvDiaryOdirDetail.text = it
                    },
                    onError = {
                        runOnUiThread {
                            Toast.makeText(
                                this,
                                "${it.message} 번역과정중 에러가 발생했습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
            } else {
                binding.tvDiaryOdirDetail.text = sourceDiary
            }
        }

        binding.btnLikeOdirDetail.setOnClickListener {

            binding.tvLikeOdirDetail.text = (likeNum + 1).toString()
            odirLikeProvider.requestSendLike(
                diaryId,
                onError = {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            )

            val toOdirList = Intent(this, HomeActivity::class.java).apply {
                putExtra("isLikeClicked", odirLikeProvider.likeResult.value?.hasLike)
            }

            setResult(Activity.RESULT_OK, toOdirList)

        }
    }

    override fun observe() {
        odirDetailProvider.diary.observe(this) {
            if (it.hasLike) {
                binding.btnLikeOdirDetail.isChecked = true
                binding.btnLikeOdirDetail.isClickable = false
            }
            binding.tvDiaryOdirDetail.text = it.content
            if (it.category == "") {
                binding.tvTagOdirDetail.text = "일상"
            } else {
                binding.tvTagOdirDetail.text = it.category
            }
            if (it.topic == "") {
                binding.tvQuestionOdirDetail.visibility = View.GONE
                binding.tvQuestionIconOdirDetail.visibility = View.GONE
            } else {
                binding.tvQuestionOdirDetail.text = "     " + it.topic
            }
            binding.tvLikeOdirDetail.text = it.likeCnt.toString()
            likeNum = it.likeCnt
            sourceDiary = it.content
            binding.tvDateOdirDetail.text = DateUtil.asString(it.createdAt())
            binding.tvNicknameOdirDetail.text = it.username
            binding.tvDescriptionOdirDetail.text = it.bio
        }
        odirLikeProvider.likeResult.observe(this) {
            if (it.hasLike) {
                binding.btnLikeOdirDetail.isChecked = true
                binding.btnLikeOdirDetail.isClickable = false
            }
        }
    }

    private val actionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            val inflater: MenuInflater = mode.menuInflater
            inflater.inflate(R.menu.menu_clip, menu)

            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            menu.removeItem(id.copy)
            menu.removeItem(id.selectAll)
            menu.removeItem(id.paste)
            // share
            menu.removeItem(id.shareText)
            // google related
            menu.removeItem(id.textAssist)

            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            val diaryId = intent.getIntExtra("diaryId", -1)

            return when (item.itemId) {
                R.id.item_menu_clip -> {
                    val start = binding.tvDiaryOdirDetail.selectionStart
                    val end = binding.tvDiaryOdirDetail.selectionEnd

                    val selection =
                        binding.tvDiaryOdirDetail.text.toString().substring(start, end).trim()

                    odirScrapProvider.requestSendScrap(
                        id = diaryId,
                        paragraph = selection,
                        onCompleted = {
                            Snackbar.make(binding.root, "스크랩이 완료되었습니다.", Snackbar.LENGTH_SHORT)
                                .show()
                        },
                        onError = {
                            Toast.makeText(
                                this@OdirDetailActivity,
                                it!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode) {}
    }

}