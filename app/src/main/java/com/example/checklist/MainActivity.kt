package com.example.checklist

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private val itemList = mutableListOf<String>()
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)

        adapter = ItemAdapter(itemList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            showAddItemDialog()
        }
    }

    private fun showAddItemDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("新しいアイテムを追加")

        val inputEditText = TextInputEditText(this)
        inputEditText.hint = "アイテム名を入力してください"

        builder.setView(inputEditText)

        builder.setPositiveButton("追加") { dialog: DialogInterface, _: Int ->
            val newItemText = inputEditText.text.toString()
            if (newItemText.isNotBlank()) {
                itemList.add(newItemText)
                adapter.notifyItemInserted(itemList.size - 1)
                // アイテムを追加した後、キーボードを非表示にする
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(inputEditText.windowToken, 0)
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("キャンセル") { dialog: DialogInterface, _: Int ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(inputEditText.windowToken, 0)
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()

        // ダイアログが表示された後、TextInputEditTextにフォーカスを設定してキーボードを表示
        inputEditText.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

}

