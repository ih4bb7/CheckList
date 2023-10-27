package com.example.checklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    // recyclerViewの変数を用意
    private lateinit var parentCheckList: ArrayList<CheckListData>
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // recyclerView表示の呪文
        recyclerView = findViewById(R.id.rv) //idの取得
        parentCheckList = ArrayList()
        val btnEdit : Button = findViewById(R.id.editBtn)
        var editPosition : Int = -1

        // recyclerAdapter を初期化
        recyclerAdapter = RecyclerAdapter(parentCheckList, object : RecyclerAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, clickedText: String) {
                editPosition = position
                val btnEditText = btnEdit.text
                val editText : EditText = findViewById(R.id.editText)
                if (btnEditText == "完了"){
                    editText.text = SpannableStringBuilder(clickedText)
                    // カーソルを文字列の最後尾に移動
                    editText.setSelection(editText.text.length)
                }
            }
        })

        recyclerView.adapter = recyclerAdapter //アダプターをセット
        recyclerView.layoutManager = LinearLayoutManager(this) //各アイテムを縦に並べてください（見せ方の指示）

        val btnAdd : Button = findViewById(R.id.addBtn)
        val et : EditText = findViewById(R.id.editText)
        btnAdd.setOnClickListener {
            val inputText = et.text.toString()
            if (inputText.isBlank()) return@setOnClickListener
            if (btnEdit.text == "完了" && editPosition != -1) {
                // 更新モードの場合、クリックされたアイテムを新しいテキストで更新
                val updatedData = CheckListData(inputText)
                parentCheckList[editPosition] = updatedData
                recyclerAdapter.notifyItemChanged(editPosition)
                editPosition = -1
            } else {
                // 追加モードの場合、新しいデータをリストに追加
                val data = CheckListData(inputText)
                parentCheckList.add(data)
                recyclerAdapter.notifyItemInserted(parentCheckList.lastIndex)
            }

            et.text = null
        }

        btnEdit.setOnClickListener {
            val btnEditText = btnEdit.text
            if (btnEditText == "編集"){
                btnEdit.text = "完了"
                btnAdd.text = "更新"
                recyclerAdapter.deleteIconVisible = true // deleteIconを表示
            } else {
                btnEdit.text = "編集"
                btnAdd.text = "追加"
                et.text = null
                recyclerAdapter.deleteIconVisible = false // deleteIconを非表示
            }
        }
    }
}