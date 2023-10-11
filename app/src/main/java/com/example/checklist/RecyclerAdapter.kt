package com.example.checklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val checkListData: ArrayList<CheckListData>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolderItem>() {

    //５）ViewHolder（インナークラス）
    inner class ViewHolderItem(v: View) :RecyclerView.ViewHolder(v) {
        val tvHolder : TextView = v.findViewById(R.id.tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        val itemXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_layout, parent, false)
        return ViewHolderItem(itemXml)
    }

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        val currentItem = checkListData[position] //何番目のリスト（アイテム）ですか
        holder.tvHolder.text = currentItem.checkList //そのリストの中の要素を指定して代入
    }

    override fun getItemCount(): Int = checkListData.size
}
