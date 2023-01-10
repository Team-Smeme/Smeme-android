package com.sopt.smeme.business.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.smeme.bridge.model.OpenDiary
import com.sopt.smeme.databinding.ItemOdirBinding

class OdirAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var odirList: List<OpenDiary> = emptyList()

    class OdirViewHolder(
        private val binding: ItemOdirBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: OpenDiary) {
            binding.tvDiaryOdir.text = endWithDots(data.content)
            binding.tvLikeOdir.text = data.likes.toString()
            binding.tvNameOdir.text = data.username
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
        return OdirViewHolder(ItemOdirBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is OdirViewHolder) holder.onBind(odirList[position])
    }

    override fun getItemCount() = odirList.size

    fun setOdirList(list: List<OpenDiary>) {
        this.odirList = list.toList()
        notifyDataSetChanged()
    }
}