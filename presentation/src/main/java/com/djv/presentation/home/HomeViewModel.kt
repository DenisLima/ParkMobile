package com.djv.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djv.domain.MusicRepository
import com.djv.domain.model.Music
import com.djv.presentation.extensions.encodeString
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val musicRepository: MusicRepository
) : ViewModel() {

    private val viewStateLv = MutableLiveData<HomeViewState>()
    fun getViewState(): LiveData<HomeViewState> = viewStateLv

    fun initEvent(homeEvent: HomeEvent) {
        when (homeEvent) {
            is HomeEvent.GetMusicList -> getMusicList(homeEvent.searchKey)
            is HomeEvent.InputSearchKey -> handleSearchButton(homeEvent.count)
        }
    }

    private fun handleSearchButton(count: Int) {
        if (count > 0) {
            viewStateLv.postValue(HomeViewState.EnableSearchButton)
        } else {
            viewStateLv.postValue(HomeViewState.DisableSearchButton)
        }
    }

    private fun getMusicList(searchKey: String) {
        viewStateLv.postValue(HomeViewState.ShowLoading)
        viewModelScope.launch {
            musicRepository.getMusics(searchKey.encodeString())
                .catch {
                    viewStateLv.postValue(HomeViewState.ErrorMessage(it.message ?: ""))
                }
                .collect {
                    viewStateLv.postValue(
                        HomeViewState.LoadedMusicList(
                            musicList = it.results
                        )
                    )
                }
        }
    }

    sealed class HomeEvent {
        data class GetMusicList(val searchKey: String) : HomeEvent()
        data class InputSearchKey(val count: Int) : HomeEvent()
    }

    sealed class HomeViewState {
        object ShowLoading : HomeViewState()
        object HiddenLoading : HomeViewState()
        data class ErrorMessage(val errorMessage: String) : HomeViewState()
        data class LoadedMusicList(val musicList: List<Music>) : HomeViewState()
        object EnableSearchButton : HomeViewState()
        object DisableSearchButton : HomeViewState()
    }
}