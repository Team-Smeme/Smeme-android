package com.sopt.smeme.business.adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sopt.smeme.bridge.controller.response.OdirListData
import com.sopt.smeme.databinding.ItemOdirBinding
import com.sopt.smeme.presentation.view.odir.OdirDetailActivity

class OdirListAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var odirList: List<OdirListData.Detail> = emptyList()

    class OdirViewHolder(
        private val binding: ItemOdirBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: OdirListData.Detail) {
            binding.tvDiaryOdir.text = endWithDots(data.content)
            binding.tvLikeOdir.text = data.likeCnt.toString()
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
        holder.itemView.setOnClickListener {
            //Snackbar.make(it, odirList[position].diaryId, Snackbar.LENGTH_SHORT).show()
            val intent = Intent(context.applicationContext, OdirDetailActivity::class.java).apply {
                val diaryId = odirList[position].diaryId
                putExtra("diaryId", diaryId)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = odirList.size

    fun setOdirList(list: List<OdirListData.Detail>) {
        this.odirList = list.toList()
        notifyDataSetChanged()
    }
}