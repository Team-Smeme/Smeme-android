package com.sopt.smeme.presentation.view.odir

import android.R.id
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.sopt.smeme.R
import com.sopt.smeme.business.viewmodel.opendiary.OdirDetailProvider
import com.sopt.smeme.business.viewmodel.opendiary.OdirScrapProvider
import com.sopt.smeme.databinding.ActivityOdirDetailBinding
import com.sopt.smeme.presentation.view.ViewBoundActivity


class OdirDetailActivity :
    ViewBoundActivity<ActivityOdirDetailBinding>(R.layout.activity_odir_detail) {

    private val odirDetailProvider: OdirDetailProvider by viewModels()
    private val odirScrapProvider: OdirScrapProvider by viewModels()
    private val diaryId = intent.getIntExtra("diaryId", -1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        constructLayout()
        listen()
        observe()
    }

    override fun constructLayout() {
        binding.tvDiaryOdirDetail.customSelectionActionModeCallback = actionModeCallback
        odirDetailProvider.requestGetDiary(
            diaryId,
            onError = {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        )
    }

    override fun listen() {

    }

    override fun observe() {
        odirDetailProvider.diary.observe(this) {

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
            // TODO: remove papago menu

            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.item_menu_clip -> {
                    val start = binding.tvDiaryOdirDetail.selectionStart
                    val end = binding.tvDiaryOdirDetail.selectionEnd

                    val selection =
                        binding.tvDiaryOdirDetail.text.toString().substring(start, end).trim()
//                    if (selection.isNotEmpty()) {
//                        Snackbar.make(binding.root, selection, Snackbar.LENGTH_SHORT).show()
//                    }
                    odirScrapProvider.requestSendScrap(
                        diaryId!!.toInt(),
                        selection,
                        onCompleted = {
                            Snackbar.make(binding.root, "스크랩이 완료되었습니다.", Snackbar.LENGTH_SHORT).show()
                        },
                        onError = {
                            Toast.makeText(this@OdirDetailActivity, it!!.message, Toast.LENGTH_SHORT).show()
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