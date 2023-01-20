package com.sopt.smeme.business.adaptor

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sopt.smeme.bridge.model.Archive
import com.sopt.smeme.databinding.ActivityWriteForeignBinding
import com.sopt.smeme.databinding.ItemArchiveExpressionBinding
import com.sopt.smeme.presentation.view.archive.ArchiveFragment
import com.sopt.smeme.presentation.view.home.HomeActivity

class ArchiveAdapter(
    private val removeItem: (Long) -> Unit = {},
    context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var archiveList: List<Archive> = emptyList()

    class ArchiveViewHolder(
        private val removeItem: (Long) -> Unit = {},
        private val binding: ItemArchiveExpressionBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Archive) {
            binding.tvItemArchive.text = data.paragraph
            binding.btnDeleteArchive.setOnClickListener{
                val builder = deleteDialog(data.id)
                builder.show()
            }
        }

        private fun deleteDialog(diaryId:Long): AlertDialog.Builder {
            val builder = AlertDialog.Builder(binding.btnDeleteArchive.context)
            builder.setTitle("삭제하시겠습니까?")
                .setCancelable(false)
                .setNegativeButton("아니오", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        Toast.makeText(binding.btnDeleteArchive.context, "삭제를 취소합니다", Toast.LENGTH_SHORT).show()
                    }
                })
                .setPositiveButton("예", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        removeItem(diaryId)
                    }

                })
                .create()
            return builder

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemArchiveExpressionBinding.inflate(inflater, parent, false)
        return ArchiveViewHolder(removeItem, binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ArchiveViewHolder) holder.onBind(archiveList[position])
    }

    override fun getItemCount() = archiveList.size

    fun setArchiveList(list: List<Archive>) {
        this.archiveList = list.toList()
        notifyDataSetChanged()
    }



}