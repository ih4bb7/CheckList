package com.example.checklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    //７）recyclerViewの変数を用意
    private var parentCheckList =ArrayList<CheckListData>()
    private lateinit var recyclerView:RecyclerView
    private val recyclerAdapter = RecyclerAdapter(parentCheckList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //８）recyclerView表示の呪文
        recyclerView =findViewById(R.id.rv) //idの取得
        recyclerView.adapter = recyclerAdapter //アダプターをセット
        recyclerView.layoutManager = LinearLayoutManager(this) //各アイテムを縦に並べてください（見せ方の指示）

        val btnAdd : Button = findViewById(R.id.addBtn)
        btnAdd.setOnClickListener {
            val et : EditText = findViewById(R.id.editText)
            val inputText = et.text.toString()
            if (inputText.isBlank()) return@setOnClickListener
            val data =CheckListData(inputText)
            parentCheckList.add(data)
            recyclerAdapter.notifyItemInserted(parentCheckList.lastIndex) //表示を更新（リストの最後に挿入）

            et.text = null
        }
    }
}