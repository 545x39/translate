package com.example.translations

import com.example.translations.data.datasource.abstraction.remote.RemoteDataSource
import com.example.translations.data.repository.implementation.DictionaryRepositoryImpl
import com.example.translations.data.repository.implementation.LanguageRepositoryImpl
import com.example.translations.data.repository.implementation.mapper.LanguageListMapper
import com.example.translations.data.repository.implementation.mapper.WordMapper
import com.example.translations.domain.entity.Word
import com.example.translations.domain.repository.abstraction.DictionaryRepository
import com.example.translations.domain.repository.abstraction.LanguageRepository
import com.example.translations.framework.datasource.implementation.local.dto.TranslationDTOImpl
import com.example.translations.framework.datasource.implementation.local.dto.WordDTOImpl
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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
        dictionaryRepository = DictionaryRepositoryImpl(localDataSource, remoteDataSource)
        languageRepository = LanguageRepositoryImpl(localDataSource, remoteDataSource)
        RxJavaPlugins.reset()
//        RxJavaPlugins.setComputationSchedulerHandler { scheduler }
        RxJavaPlugins.setIoSchedulerHandler { scheduler }
        localDataSource.clear()
    }

    @After
    fun tearDown() = RxJavaPlugins.reset()

    @Test
    fun `when no languages are present then fetch from remote source`() {
        //Given
        Mockito.`when`(remoteDataSource.fetchLanguages())
            .thenReturn(Single.just(fakeLanguagesResponse))

        //When
        languageRepository.getLanguages()
            .test()
            .dispose()
        advanceTime()

        //Then
        verify(remoteDataSource, times(1)).fetchLanguages()
        assertEquals(fakeLanguagesResponse.languages.size, localDataSource.languages.size)
    }

    @Test
    fun `when languages are present then get from database`() {
        //Given
        Mockito.`when`(remoteDataSource.fetchLanguages())
            .thenReturn(Single.just(fakeLanguagesResponse))

        //When
        localDataSource.languages.addAll(fakeLocalLanguages)
        val observer = languageRepository.getLanguages()
            .test()
        advanceTime()

        //Then
        observer.assertValueCount(1).dispose()
        verify(remoteDataSource, times(0)).fetchLanguages()
    }

    @Test
    fun `when no translations are present then fetch from remote source`() {
        //Given
        localDataSource.languages.addAll(fakeLocalLanguages)
        val languages = LanguageListMapper().map(fakeLocalLanguages)
        val fromLanguage = languages[0]
        val toLanguage = languages[1]
        val word = Word("слово", fromLanguage.id)
        Mockito.`when`(
            remoteDataSource.fetchTranslation(
                word.value,
                fromLanguage.code,
                toLanguage.code
            )
        ).thenReturn(Single.just(fakeTranslationResponse))
        localDataSource.words.add(WordDTOImpl(word.value, fromLanguage.id, true))

        //When
        val observer = dictionaryRepository.translate(word, fromLanguage, toLanguage).test()
        advanceTime()

        //Then
        observer.assertValueCount(1)
            .assertValue("word")
            .dispose()
        verify(remoteDataSource, times(1)).fetchTranslation(
            word.value,
            fromLanguage.code,
            toLanguage.code
        )
        assertEquals(WordDTOImpl(word.value, word.language, true), localDataSource.words[0])
        assertEquals(WordDTOImpl("word", toLanguage.id, false), localDataSource.words[1])
        assertEquals(
            TranslationDTOImpl(word.id, 1, fromLanguage.id, toLanguage.id),
            localDataSource.translations[0]
        )
    }

    @Test
    fun `when translations are present then get from database`() {
        //Given
        val languages = LanguageListMapper().map(fakeLocalLanguages)
        val fromLanguage = languages[0]
        val toLanguage = languages[1]
        val query = WordDTOImpl("слово", fromLanguage.id, true)
        val translation = WordDTOImpl("word", toLanguage.id)
        fillTranslationData(query, translation)

        //When
        val observer =
            dictionaryRepository.translate(WordMapper().map(query), fromLanguage, toLanguage)
                .test()
                .dispose()
        advanceTime()

        //Then
        verify(remoteDataSource, times(0)).fetchTranslation(
            query.value,
            fromLanguage.code,
            toLanguage.code
        )
    }

    private fun fillTranslationData(
        query: WordDTOImpl,
        translation: WordDTOImpl
    ) {
        localDataSource.apply {
            this.languages.addAll(fakeLocalLanguages)
            words.addAll(listOf(query, translation))
            translations.add(
                TranslationDTOImpl(
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
