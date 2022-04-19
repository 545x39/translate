package com.example.dictionary.framework.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.R
import com.example.dictionary.domain.usecase.event.implementation.view.ViewEvent
import com.example.translations.domain.entity.DictionaryEntry
import com.example.translations.domain.entity.Language
import com.example.translations.util.rx.RxBus
import timber.log.Timber

class DictionaryAdapter(
    private val rxBus: RxBus,
    private val languages: MutableList<Language> = mutableListOf()
) : RecyclerView.Adapter<ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<DictionaryEntry>() {
        override fun areItemsTheSame(oldItem: DictionaryEntry, newItem: DictionaryEntry) =
            oldItem.word == newItem.word

        override fun areContentsTheSame(
            oldItem: DictionaryEntry,
            newItem: DictionaryEntry
        ) = areItemsTheSame(oldItem, newItem)
                && oldItem.translations.size == newItem.translations.size
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<DictionaryEntry>) {
        differ.submitList(list)
    }

    fun setLanguages(list: List<Language>) {
        languages.apply {
            clear()
            addAll(list)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_dictionary_entry_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        differ.currentList[position].apply {
            holder.title.text = word.value
            holder.deleteEntryButton.setOnClickListener {
                rxBus.send(ViewEvent.DeleteEntry(this))
            }
            holder.translations.text = buildString {
                translations.map {
                    append("${getLanguageName(it.language)}${it.value}\n")
                }
            }
        }
    }

    private fun getLanguageName(languageId: Long): String {
        languages.map {
            if (it.id == languageId) return "${it.name}: "
        }
        return ""
    }

    override fun getItemCount() = differ.currentList.size
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = itemView.findViewById(R.id.title)
    val deleteEntryButton: ImageButton = itemView.findViewById(R.id.button_delete_entry)
    val translations: TextView = itemView.findViewById(R.id.translations_text_view)
}