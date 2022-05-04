package com.example.translations

import com.example.translations.data.source.remote.RemoteDataSource
import com.example.translations.data.repository.DictionaryRepositoryImpl
import com.example.translations.data.repository.LanguageRepositoryImpl
import com.example.translations.data.repository.mapper.*
import com.example.translations.domain.entity.Word
import com.example.translations.domain.repository.DictionaryRepository
import com.example.translations.domain.repository.LanguageRepository
import com.example.translations.framework.datasource.local.dto.TranslationDtoImpl
import com.example.translations.framework.datasource.local.dto.WordDtoImpl
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class RepositoryTests {

    private lateinit var languageRepository: LanguageRepository

    private lateinit var dictionaryRepository: DictionaryRepository

    private val localDataSource = FakeLocalDataSource()

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    private val scheduler = TestScheduler()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val wm = WordMapper()
        dictionaryRepository = DictionaryRepositoryImpl(
            localDataSource,
            remoteDataSource,
            wm,
            DictionaryEntryMapper(wm),
            TranslationResponseMapper()
        )
        languageRepository = LanguageRepositoryImpl(
            localDataSource,
            remoteDataSource,
            LanguageResponseMapper(),
            LocalLanguageDtoMapper()
        )
        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler { scheduler }
        localDataSource.clear()
    }

    @After
    fun tearDown() = RxJavaPlugins.reset()

    @Test
    fun `when no languages are present then fetch from remote source`() {
        //When
        Mockito.`when`(remoteDataSource.fetchLanguages())
            .thenReturn(Observable.just(fakeLanguagesResponse))

        //Action
        languageRepository.getLanguages()
            .test()
            .dispose()
        advanceTime()

        //Verify
        verify(remoteDataSource, times(1)).fetchLanguages()
        assertEquals(fakeLanguagesResponse.languages.size, localDataSource.languages.size)
    }

    @Test
    fun `when languages are present then get from database`() {
        //When
        Mockito.`when`(remoteDataSource.fetchLanguages())
            .thenReturn(Observable.just(fakeLanguagesResponse))

        //Action
        localDataSource.languages.addAll(fakeLocalLanguages)
        val observer = languageRepository.getLanguages()
            .test()
        advanceTime()

        //Verify
        observer.assertValueCount(1).dispose()
        verify(remoteDataSource, times(0)).fetchLanguages()
    }

    @Test
    fun `when no translations are present then fetch from remote source`() {
        //When
        localDataSource.languages.addAll(fakeLocalLanguages)
        val languages = LanguageListMapper().map(fakeLocalLanguages)
        val fromLanguage = languages[0]
        val toLanguage = languages[1]
        val word = Word("слово", fromLanguage.id, 1)
        Mockito.`when`(
            remoteDataSource.fetchTranslation(
                word.value,
                fromLanguage.code,
                toLanguage.code
            )
        ).thenReturn(Observable.just(fakeTranslationResponse))
        localDataSource.words.add(WordDtoImpl(word.value, fromLanguage.id, true))

        //Action
        val observer = dictionaryRepository.translate(word, fromLanguage, toLanguage).test()
        advanceTime()

        //Verify
        verify(remoteDataSource, times(1)).fetchTranslation(
            word.value,
            fromLanguage.code,
            toLanguage.code
        )
        observer.assertValueCount(1)
            .assertValue("word")
            .dispose()
        assertEquals(WordDtoImpl(word.value, word.language, true), localDataSource.words[0])
        assertEquals(WordDtoImpl("word", toLanguage.id, false), localDataSource.words[1])
        assertEquals(
            TranslationDtoImpl(word.id, 1, fromLanguage.id, toLanguage.id),
            localDataSource.translations[0]
        )
    }

    @Test
    fun `when translations are present then get from database`() {
        //When
        val languages = LanguageListMapper().map(fakeLocalLanguages)
        val fromLanguage = languages[0]
        val toLanguage = languages[1]
        val query = WordDtoImpl("слово", fromLanguage.id, true).also { it.id = 0 }
        val translation = WordDtoImpl("word", toLanguage.id).also { it.id = 1 }
        fillTranslationData(query, translation)

        Mockito.`when`(
            remoteDataSource.fetchTranslation(
                query.value,
                fromLanguage.code,
                toLanguage.code
            )
        ).thenReturn(Observable.just(fakeTranslationResponse))

        //Action
        dictionaryRepository.translate(WordMapper().map(query), fromLanguage, toLanguage)
            .test()
            .dispose()
        advanceTime()

        //Verify
        verify(remoteDataSource, times(0)).fetchTranslation(
            query.value,
            fromLanguage.code,
            toLanguage.code
        )
    }

    private fun fillTranslationData(
        query: WordDtoImpl,
        translation: WordDtoImpl
    ) {
        localDataSource.apply {
            this.languages.addAll(fakeLocalLanguages)
            words.addAll(listOf(query, translation))
            translations.add(
                TranslationDtoImpl(
                    query.id,
                    translation.id,
                    query.language,
                    translation.language
                )
            )
        }
    }

    private fun advanceTime() = scheduler.advanceTimeBy(20L, TimeUnit.MILLISECONDS)
}
