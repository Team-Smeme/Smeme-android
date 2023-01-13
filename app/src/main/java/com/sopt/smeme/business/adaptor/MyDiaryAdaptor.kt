package com.sopt.smeme.business.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.smeme.TextUtil
import com.sopt.smeme.bridge.model.MyDiary
import com.sopt.smeme.databinding.ItemMyDiaryBinding

class MyDiaryAdaptor(
    private val navigateToDetail: (Long) -> Unit = {},
    context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var myDiaryList: List<MyDiary> = emptyList()

    class MyDiaryViewHolder(
        private val navigateToDetail: (Long) -> Unit = {},
        private var binding: ItemMyDiaryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindDiary(data: MyDiary) {
            binding.txtDiaryTime.text = data.myDiaryTime
            binding.txtMyDiary.text = TextUtil.wordControl(data.myDiary)
            binding.clMyDiary.setOnClickListener {
                navigateToDetail(data.diaryId)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMyDiaryBinding.inflate(inflater, parent, false)
        return MyDiaryViewHolder(navigateToDetail, binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyDiaryViewHolder) holder.bindDiary(myDiaryList[position])
    }

    override fun getItemCount() = myDiaryList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDiaryList(myDiaryList: List<MyDiary>) {
        this.myDiaryList = myDiaryList.toList()
        notifyDataSetChanged()
    }
}