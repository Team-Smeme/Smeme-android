package com.sopt.smeme.business.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.smeme.bridge.model.MyDiary
import com.sopt.smeme.databinding.ItemMyDiaryBinding

class MyDiaryAdaptor(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var myDiaryList: List<MyDiary> = emptyList()

    class MyDiaryViewHolder(
        private var binding: ItemMyDiaryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindDiary(data: MyDiary) {
            binding.txtDiaryTime.text = data.myDiaryTime
            binding.txtMyDiary.text = data.myDiary
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyDiaryViewHolder(ItemMyDiaryBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyDiaryViewHolder) holder.bindDiary(myDiaryList[position])
    }

    override fun getItemCount() = myDiaryList.size

    fun setDiaryList(myDiaryList: List<MyDiary>) {
        this.myDiaryList = myDiaryList.toList()
        notifyDataSetChanged()
    }

}