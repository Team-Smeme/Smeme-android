package com.sopt.smeme.presentation.view.mdir

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id
import com.google.android.material.snackbar.Snackbar
import com.sopt.smeme.R
import com.sopt.smeme.business.viewmodel.mydiary.MdirDetailProvider
import com.sopt.smeme.databinding.ActivityMyDiaryDetailBinding
import com.sopt.smeme.presentation.view.ViewBoundActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.Bidi

@AndroidEntryPoint
class MdirDetailActivity :
    ViewBoundActivity<ActivityMyDiaryDetailBinding>(R.layout.activity_my_diary_detail) {

    private val mdirDetailProvider:MdirDetailProvider by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnOptionMdirDetail.setOnClickListener{
            showPopup(binding.btnOptionMdirDetail)
        }
        listen()
    }

    override fun constructLayout() {
        val diaryId = intent.getLongExtra("diaryId",85)

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
            finish()
        }
    }

    override fun observe() {
        mdirDetailProvider.diary.observe(this){
            binding.tvDiaryMdirDetail.text = it.content
            binding.tvTagMdirDetail.text = it.category
            binding.tvQuestionMdirDetail.text = it.topic
            binding.tvLikeNumber.text = it.likeCnt.toString()
            binding.tvDateMdirDetail.text = it.createdAt
            if(it.isPublic){
                binding.tvPublic.text = "공개"
                binding.tvPublic.visibility = View.VISIBLE
            }
            else{
                binding.tvPublic.visibility = View.INVISIBLE
            }

        }
    }

    private fun showPopup(v: View){
        val popup = PopupMenu(this, v)
        popup.menuInflater.inflate(R.menu.menu_option, popup.menu)
        var listener = PopupMenuListener()
        popup.setOnMenuItemClickListener(listener)
        popup.show()
    }

    inner class PopupMenuListener: PopupMenu.OnMenuItemClickListener{
        override fun onMenuItemClick(item: MenuItem?): Boolean {
            when(item?.itemId){
                R.id.menu_delete ->
                    Snackbar.make(binding.root, "삭제하시겠습니까?", Snackbar.LENGTH_SHORT).show()

            }
            return false
        }

    }

}