package com.djv.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djv.domain.MusicRepository
import com.djv.domain.model.Music
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val musicRepository: MusicRepository
): ViewModel() {

    private val viewStateLv = MutableLiveData<HomeViewState>()
    fun getViewState(): LiveData<HomeViewState> = viewStateLv

    fun initEvent(homeEvent: HomeEvent) {
        when(homeEvent) {
            is HomeEvent.GetMusicList -> getMusicList(homeEvent.searchKey)
        }
    }

    private fun getMusicList(searchKey: String) {
        HomeViewState.ShowLoading
        viewModelScope.launch {
            musicRepository.getMusics(searchKey)
                .catch {
                    HomeViewState.HiddenLoading
                    HomeViewState.ErrorMessage(it.message ?: "")
                }
                .collect {
                    HomeViewState.HiddenLoading
                    viewStateLv.postValue(
                        HomeViewState.LoadedMusicList(
                            musicList = it.results
                        )
                    )
                }
        }
    }

    sealed class HomeEvent {
        data class GetMusicList(val searchKey: String): HomeEvent()
    }

    sealed class HomeViewState {
        object ShowLoading: HomeViewState()
        object HiddenLoading: HomeViewState()
        data class ErrorMessage(val errorMessage: String): HomeViewState()
        data class LoadedMusicList(val musicList: List<Music>): HomeViewState()
    }
}