package com.example.dictionary.framework.presentation.ui

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.R
import com.example.dictionary.di.DaggerDictionaryComponent
import com.example.dictionary.domain.usecase.event.data.DataEvent
import com.example.dictionary.domain.usecase.event.view.ViewEvent
import com.example.dictionary.domain.usecase.factory.UseCaseFactory
import com.example.dictionary.framework.presentation.ui.adapter.DictionaryAdapter
import com.example.dictionary.framework.presentation.ui.adapter.LanguageAdapter
import com.example.translations.App
import com.example.translations.domain.entity.DictionaryEntry
import com.example.translations.domain.entity.Language
import com.example.translations.domain.usecase.event.Error
import com.example.translations.domain.usecase.event.Event
import com.example.translations.framework.presentation.ui.MainActivity
import com.example.translations.util.ifTrue
import com.example.translations.util.RxBus
import com.jakewharton.rxbinding4.appcompat.queryTextChanges
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers.computation
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class DictionaryFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()

    private val switchObservable = PublishSubject.create<Boolean>()

    lateinit var disposable: Disposable

    @Inject
    lateinit var rxBus: RxBus

    @Inject
    lateinit var factory: UseCaseFactory

    private lateinit var queryEditText: EditText

    private lateinit var translationTextView: TextView

    private lateinit var fromLanguageSpinner: Spinner

    private lateinit var toLanguageSpinner: Spinner

    private lateinit var recyclerView: RecyclerView

    private lateinit var progress: ProgressBar

    private lateinit var translateButton: Button

    private lateinit var languageAdapter: LanguageAdapter

    private lateinit var dictionaryAdapter: DictionaryAdapter

    private var fromLanguage: Language? = null

    private var toLanguage: Language? = null

    private fun handleEvent(event: Event) {
        when (event) {
            is DataEvent.Loading -> showProgress(true)
            is DataEvent.Failure -> onError(event)
            is DataEvent.LanguagesLoaded -> onLanguagesLoaded(event.languages)
            is DataEvent.DictionaryLoaded -> onDictionaryLoaded(event.entries)
            is DataEvent.TranslationCompleted -> onTranslationComplete(event.translation)
            is DataEvent.QuerySaved -> reloadDictionary()
            is DataEvent.EntryDeleted -> reloadDictionary()
            is ViewEvent.GetLanguages -> factory.getLanguagesUseCase().execute()
            is ViewEvent.Translate -> onTranslate(event)
            is ViewEvent.GetDictionary -> factory.getDictionaryUseCase(event.query).execute()
            is ViewEvent.DeleteEntry -> factory.deleteEntryUseCase(event.entry).execute()
        }
    }

    private fun onTranslate(event: ViewEvent.Translate) {
        factory.translateUseCase(
            event.query,
            event.fromLanguage,
            event.toLanguage
        ).execute()
    }

    private fun reloadDictionary() {
        rxBus.send(ViewEvent.GetDictionary())
    }

    private fun showProgress(show: Boolean) {
        progress.visibility = if (show) VISIBLE else GONE
    }

    private fun onTranslationComplete(translation: String) {
        showProgress(false)
        translationTextView.text = translation
        reloadDictionary()
    }

    private fun onDictionaryLoaded(entries: List<DictionaryEntry>) {
        showProgress(false)
        Timber.e("===== GOT ENTRIES: $entries")
        dictionaryAdapter.apply {
            setLanguages(languageAdapter.languages)
            submitList(entries)
            recyclerView.scrollToPosition(0)
        }
    }

    private fun onLanguagesLoaded(languages: List<Language>) {
        languageAdapter.languages.apply {
            clear()
            addAll(languages)
        }
        (fromLanguage == null).ifTrue {
            languages.find { it.code == "ru" }?.let {
                fromLanguage = it
                fromLanguageSpinner.setSelection(it.id.toInt() - 1)
            }
        }
        (toLanguage == null).ifTrue {
            languages.find { it.code == "en" }?.let {
                toLanguage = it
                toLanguageSpinner.setSelection(it.id.toInt() - 1)
            }
        }
        languageAdapter.notifyDataSetChanged()
        reloadDictionary()
    }

    private fun onError(event: DataEvent.Failure) {
        showProgress(false)
        (requireActivity() as MainActivity).showSnackBar(
            when (event.error) {
                is Error.Unknown -> resources.getString(R.string.error_unknown)
                is Error.CouldNotLoadLanguages -> resources.getString(R.string.error_languages_not_loaded)
                is Error.TranslationNotFound -> resources.getString(R.string.error_translation_not_found)
                is Error.SourceLanguageNotFound -> resources.getString(R.string.error_source_language_not_found)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DaggerDictionaryComponent
            .factory()
            .create((requireActivity().application as App).appComponent)
            .inject(this)
        return inflater.inflate(R.layout.fragment_dictionary_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        with(view) {
            queryEditText = findViewById(R.id.edit_text)
            translationTextView = findViewById(R.id.translation_text_view)
            translateButton = findViewById(R.id.translate_button)
            fromLanguageSpinner = findViewById(R.id.from_language)
            toLanguageSpinner = findViewById(R.id.to_language)
            recyclerView = findViewById(R.id.recycler_view)
            progress = findViewById(R.id.progress_bar)
        }
        languageAdapter = LanguageAdapter(requireContext())
        dictionaryAdapter = DictionaryAdapter(rxBus)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
            .also { it.orientation = LinearLayoutManager.VERTICAL }
        recyclerView.adapter = dictionaryAdapter
        fromLanguageSpinner.adapter = languageAdapter
        toLanguageSpinner.adapter = languageAdapter
        fromLanguageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                fromLanguage = languageAdapter.getItem(position)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }
        toLanguageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                toLanguage = languageAdapter.getItem(position)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }
        translateButton.clicks()
            .filter { queryEditText.text.isNotBlank() }
            .throttleFirst(1500, TimeUnit.MILLISECONDS)
            .subscribe {
                rxBus.send(
                    ViewEvent.Translate(
                        queryEditText.text.toString(),
                        fromLanguage!!,
                        toLanguage!!
                    )
                )
            }.also { compositeDisposable.add(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        (menu.findItem(R.id.action_search).actionView as SearchView).queryTextChanges()
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribe { rxBus.send(ViewEvent.GetDictionary(it.toString())) }
            .also { compositeDisposable.add(it) }
    }

    override fun onStart() {
        super.onStart()
        disposable = switchObservable
            .switchMap { if (it) rxBus.get() else Observable.never() }
            .subscribeOn(computation())
            .observeOn(mainThread())
            .subscribe({ handleEvent(it) }, {})
            .also { compositeDisposable.add(it) }
    }

    override fun onResume() {
        super.onResume()
        switchObservable.onNext(true)
        rxBus.send(ViewEvent.GetLanguages)
    }

    override fun onPause() {
        super.onPause()
        switchObservable.onNext(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}