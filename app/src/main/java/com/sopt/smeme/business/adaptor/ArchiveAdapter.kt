package com.sopt.smeme.business.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.smeme.bridge.model.Archive
import com.sopt.smeme.databinding.ItemArchiveExpressionBinding

class ArchiveAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var archiveList: List<Archive> = emptyList()

    class ArchiveViewHolder(
        private val binding: ItemArchiveExpressionBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Archive) {
            binding.tvItemArchive.text = data.paragraph
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemArchiveExpressionBinding.inflate(inflater, parent, false)
        return ArchiveViewHolder(binding)
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