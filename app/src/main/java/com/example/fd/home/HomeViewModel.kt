package com.example.fd.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fd.remotedata.NapsterService
import com.example.musicstreaming.services.dtos.Artist
import com.example.musicstreaming.services.dtos.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val napsterService: NapsterService) : ViewModel() {

    private val _topArtists: MutableLiveData<List<Artist>> = MutableLiveData(emptyList())
    val topArtists: LiveData<List<Artist>> = _topArtists

    private val _isLoadingHome: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoadingHome: LiveData<Boolean> = _isLoadingHome

    private val _isLoadingSearch: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoadingSearch: LiveData<Boolean> = _isLoadingSearch

    private val _searchTracks: MutableLiveData<List<Track>> = MutableLiveData(emptyList())
    val searchTracks: LiveData<List<Track>> = _searchTracks

    fun getTopArtists() {
        viewModelScope.launch {
            _isLoadingHome.value = true
            val result = napsterService.getTopArtists()
            delay(500)
            val artistsList = result?.artists
            artistsList?.forEach {
                val trackResult = napsterService.getTopTracks(it.id)
                it.topTracks = trackResult?.tracks
            }
            _isLoadingHome.value = false
            _topArtists.value = result?.artists
        }
    }

    fun search(trackName: String) {
        viewModelScope.launch {
            _isLoadingSearch.value = true
            val result = napsterService.searchTracks(trackName)
            delay(500)
            _isLoadingSearch.value = false
            _searchTracks.value = result?.searchTrackResult?.data?.tracks
        }
    }

}