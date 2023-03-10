package com.sopt.smeme.business.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.smeme.bridge.controller.response.OdirListData
import com.sopt.smeme.databinding.ItemOdirBinding

class OdirListAdapter(
    private val navigateToDetail: (Int) -> Unit,
    context: Context,
    private val updatePosition: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var odirList: List<OdirListData.Detail> = emptyList()

    class OdirListViewHolder(
        private val navigateToDetail: (Int) -> Unit,
        private val binding: ItemOdirBinding,
        private val updatePosition: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: OdirListData.Detail) {
            if (data.isSeen) {
                binding.tvDiaryOdir.setTextColor(Color.parseColor("#d2d2d2"))
            } else {
                binding.tvDiaryOdir.setTextColor(Color.parseColor("#171716"))
            }
            binding.tvDiaryOdir.text = endWithDots(data.content)
            binding.tvLikeOdir.text = data.likeCnt.toString()
            binding.tvNameOdir.text = data.username
            binding.clItemOdir.setOnClickListener {
                navigateToDetail(data.diaryId)
                updatePosition(adapterPosition)
                data.isSeen = true
            }

        }

        private fun endWithDots(text: String): String {
            return if (text.length > 145) {
                text.substring(0 until 145).trimEnd() + " ..."
            } else {
                text
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemOdirBinding.inflate(inflater, parent, false)
        return OdirListViewHolder(navigateToDetail, binding, updatePosition)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is OdirListViewHolder) holder.onBind(odirList[position])

    }

    override fun getItemCount() = odirList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setOdirList(list: List<OdirListData.Detail>) {
        this.odirList = list.toList()
        notifyDataSetChanged()
    }

    fun resetOdirList(index: Int) {
        notifyItemChanged(index)
    }

    fun plusLikeCount(position: Int) {
        odirList[position].likeCnt += 1
    }

}