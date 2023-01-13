package com.sopt.smeme.presentation.view.mdir

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import com.sopt.smeme.R
import com.sopt.smeme.business.viewmodel.mydiary.DeleteMdir
import com.sopt.smeme.business.viewmodel.mydiary.MdirDetailProvider
import com.sopt.smeme.databinding.ActivityMyDiaryDetailBinding
import com.sopt.smeme.presentation.view.ViewBoundActivity
import com.sopt.smeme.presentation.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MdirDetailActivity :
    ViewBoundActivity<ActivityMyDiaryDetailBinding>(R.layout.activity_my_diary_detail) {

    private val mdirDetailProvider: MdirDetailProvider by viewModels()
    private val deleteMdir: DeleteMdir by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnOptionMdirDetail.setOnClickListener {
            showPopup(binding.btnOptionMdirDetail)
        }
        listen()
    }

    override fun constructLayout() {
        val diaryId = intent.getLongExtra("diaryId", -1)

        mdirDetailProvider.requestGetDiary(
            diaryId,
            onError = {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        )

    }

    override fun listen() {
        super.listen()
        binding.btnBackMdirDetail.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    override fun observe() {
        mdirDetailProvider.diary.observe(this) {
            binding.tvDiaryMdirDetail.text = it.content
            binding.tvTagMdirDetail.text = if (it.category.isEmpty()) "일상" else it.category
            if (it.topicId.toString() == "0") {
                binding.tvQuestionMdirDetail.visibility = View.GONE
                binding.tvQuestionIconMdirDetail.visibility = View.GONE
            } else {
                binding.tvQuestionMdirDetail.text = "     " + it.topic
            }
            if (!it.isPublic){ binding.tvLikeNumber.visibility = View.GONE
                binding.btnLikeMdirDetail.visibility = View.GONE
            }
            binding.tvLikeNumber.text = it.likeCnt.toString() + " 개의 추천"
            binding.tvDateMdirDetail.text = it.createdAt
            if (it.isPublic) {
                binding.tvPublic.text = "공개"
                binding.tvPublic.visibility = View.VISIBLE
            } else {
                binding.tvPublic.visibility = View.INVISIBLE
            }

        }

    }

    private fun showPopup(v: View) {
        val popup = PopupMenu(this, v)
        popup.menuInflater.inflate(R.menu.menu_option, popup.menu)
        var listener = PopupMenuListener()
        popup.setOnMenuItemClickListener(listener)
        popup.show()
    }

    inner class PopupMenuListener : PopupMenu.OnMenuItemClickListener {
        override fun onMenuItemClick(item: MenuItem?): Boolean {
            val builder = deleteDialog()
            when (item?.itemId) {
                R.id.menu_delete -> builder.show()

            }
            return false
        }

    }

    fun deleteDialog(): AlertDialog.Builder {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("삭제하시겠습니까?")
            .setCancelable(false)
            .setNegativeButton("아니오", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    Toast.makeText(this@MdirDetailActivity, "삭제를 취소합니다", Toast.LENGTH_SHORT).show()
                }
            })
            .setPositiveButton("예", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    val diaryId = intent.getLongExtra("diaryId", -1).toString()

                    if (diaryId != null) {
                        deleteMdir.deleteDiary(
                            diaryId,
                            onError = {
                                Toast.makeText(
                                    this@MdirDetailActivity,
                                    it.message,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        )
                        startActivity(Intent(this@MdirDetailActivity, HomeActivity::class.java))
                        finish()
                    }
                }

            })
            .create()
        return builder

    }

}