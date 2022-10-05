package com.djv.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.djv.domain.MusicRepository
import com.djv.domain.model.SearchMusicResult
import com.djv.presentation.home.HomeViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    @MockK
    lateinit var musicRepository: MusicRepository

    private val viewModel by lazy {
        spyk(
            HomeViewModel(
                musicRepository = musicRepository
            )
        )
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN user needs get music list WHEN tapped in search button THEN get success`() {
        dispatcher.runBlockingTest {
            //Given
            coEvery {
                musicRepository.getMusics("fakeSearch")
            } returns flow { FAKE_RESPONSE }

            //When
            viewModel.initEvent(HomeViewModel.HomeEvent.GetMusicList("fakeSearch"))

            //Then
            assertEquals(HomeViewModel.HomeViewState.ShowLoading, viewModel.getViewState().value)
        }
    }

    companion object {
        private val FAKE_RESPONSE = SearchMusicResult(
            resultCount = 10,
            results = listOf()
        )
    }
}