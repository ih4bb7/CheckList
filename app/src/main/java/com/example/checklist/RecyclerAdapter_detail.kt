package com.example.checklist

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections

class RecyclerAdapter_detail(private val checkListData: ArrayList<CheckListData>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerAdapter_detail.ViewHolderItem>() {

    var deleteIconVisible: Boolean = false
        set(value) {
            field = value
            notifyDataSetChanged() // フラグが変更されたらリスト全体を更新
        }

    // アイテム移動の処理
    fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(checkListData, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    //５）ViewHolder（インナークラス）
    inner class ViewHolderItem(v:View,rAdapter:RecyclerAdapter_detail) :RecyclerView.ViewHolder(v) {
        val tvHolder : TextView = v.findViewById(R.id.tv)
        val deleteIcon: ImageView = v.findViewById(R.id.deleteIcon)

        fun bind(data: CheckListData) {
            // 他のバインド処理
            if (deleteIconVisible) {
                deleteIcon.visibility = View.VISIBLE
            } else {
                deleteIcon.visibility = View.GONE
            }
        }

        // クリック処理(1行分の画面(view)が押されたら～)
        init {
            v.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(itemView, position, checkListData[position].checkList)
                }
            }

            // deleteIconのクリック処理
            deleteIcon.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onDeleteIconClick(itemView, position)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, clickedText: String)
        fun onDeleteIconClick(view: View, position: Int)
    }

    // メソッドの追加
    fun deleteItem(position: Int) {
        checkListData.removeAt(position)
        notifyItemRemoved(position)
    }

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    fun getSelectedPosition(): Int {
        return selectedPosition
    }

    fun setSelectedPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        val itemXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_layout, parent, false)
        return ViewHolderItem(itemXml, this)
    }

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        val currentItem = checkListData[position] //何番目のリスト（アイテム）ですか
        holder.bind(currentItem) // bindメソッドを呼び出してアイテムをバインド
        holder.tvHolder.text = currentItem.checkList //そのリストの中の要素を指定して代入

        // クリックイベントをリスナーに登録
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(holder.itemView, position, currentItem.checkList)
        }
    }

    override fun getItemCount(): Int = checkListData.size
}
