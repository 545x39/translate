package com.example.dictionary.framework.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.dictionary.R
import com.example.translations.domain.entity.Language

class LanguageAdapter(private val context: Context) : BaseAdapter() {

    val languages = mutableListOf<Language>()

    override fun getCount() = languages.size

    override fun getItem(position: Int) = languages[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {

        fun View.setText() = this.apply {
            view.apply {
                findViewById<TextView>(R.id.value_text).text = languages[position].name
            }
        }

        return when (view) {
            null -> (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.row_simple_list_layout, viewGroup, false)
                .setText()
            else -> view.setText()
        }
    }
}