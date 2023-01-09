package com.sopt.smeme.presentation.view.odir

import android.R.id
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.sopt.smeme.R
import com.sopt.smeme.databinding.ActivityOdirDetailBinding
import com.sopt.smeme.presentation.view.ViewBoundActivity


class OdirDetailActivity :
    ViewBoundActivity<ActivityOdirDetailBinding>(R.layout.activity_odir_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvDiaryOdirDetail.customSelectionActionModeCallback = actionModeCallback
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
            return when (item.itemId) {
                R.id.item_menu_clip -> {
                    val start = binding.tvDiaryOdirDetail.selectionStart
                    val end = binding.tvDiaryOdirDetail.selectionEnd

                    val selection =
                        binding.tvDiaryOdirDetail.text.toString().substring(start, end).trim()
                    if (selection.isNotEmpty()) {
                        Snackbar.make(binding.root, selection, Snackbar.LENGTH_SHORT).show()
                    }
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode) {}
    }
}