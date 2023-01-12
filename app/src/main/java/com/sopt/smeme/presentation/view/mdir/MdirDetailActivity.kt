package com.sopt.smeme.presentation.view.mdir

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import com.google.android.material.snackbar.Snackbar
import com.sopt.smeme.R
import com.sopt.smeme.databinding.ActivityMyDiaryDetailBinding
import com.sopt.smeme.presentation.view.ViewBoundActivity

class MdirDetailActivity :
    ViewBoundActivity<ActivityMyDiaryDetailBinding>(R.layout.activity_my_diary_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnOptionMdirDetail.setOnClickListener{
            showPopup(binding.btnOptionMdirDetail)
        }
        listen()
    }

    override fun listen() {
        super.listen()
        binding.btnBackMdirDetail.setOnClickListener {
            finish()
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