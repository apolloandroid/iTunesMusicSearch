package com.example.itunesmusicsearch.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.itunesmusicsearch.data.Repository
import com.example.itunesmusicsearch.data.database.Album
import com.example.itunesmusicsearch.ui.search.adapters.OnAlbumsListItemClickListener
import com.example.itunesmusicsearch.utility.ConnectionStatus
import kotlinx.coroutines.*

class SearchViewModel(
    private val repository: Repository,
    private val connectionStatus: ConnectionStatus
) : ViewModel(), OnAlbumsListItemClickListener {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    var searchHistory: LiveData<List<Album>> = repository.searchHistory

    private val _searchResults = MutableLiveData<List<Album>>()
    val searchResults: LiveData<List<Album>>
        get() = _searchResults

    private val _makeShadowBackground = MutableLiveData<Boolean>()
    val makeShadowBackground: LiveData<Boolean>
        get() = _makeShadowBackground

    private val _showProgressBarLoadingTracks = MutableLiveData<Boolean>()
    val showProgressBarLoadingTracks: LiveData<Boolean>
        get() = _showProgressBarLoadingTracks

    private val _showNoInternetConnectionNotification = MutableLiveData<Boolean>()
    val showNoInternetConnectionMessage: LiveData<Boolean>
        get() = _showNoInternetConnectionNotification

    private val _hideSearchResults = MutableLiveData<Boolean>()
    val hideSearchResults: LiveData<Boolean>
        get() = _hideSearchResults

    private val _clearSearchQuery = MutableLiveData<Boolean>()
    val clearSearchQuery: LiveData<Boolean>
        get() = _clearSearchQuery

    private val _hideKeyBoard = MutableLiveData<Boolean>()
    val hideKeyBoard: LiveData<Boolean>
        get() = _hideKeyBoard

    private val _navigateToAlbumDetails = MutableLiveData<Album>()
    val navigateToAlbumDetails: LiveData<Album>
        get() = _navigateToAlbumDetails

    // Handles AlbumList Item click
    override fun onItemClick(album: Album) {
        if (!checkConnectionStatus()) return

        _clearSearchQuery.value = true

        viewModelScope.launch {
            _showProgressBarLoadingTracks.postValue(true)
            val tracks = repository.getTracks(album.collectionId)
            album.tracks = tracks
            repository.addAlbumToSearchHistory(album)
            _navigateToAlbumDetails.postValue(album)
            _showProgressBarLoadingTracks.postValue(false)
            _hideKeyBoard.postValue(true)
        }
    }

    override fun onCleared() {
        viewModelScope.coroutineContext.cancel()
        super.onCleared()
    }

    // Handles SearchView text changes
    fun onSearchQueryTextChanged(query: String) {
        if (!checkConnectionStatus()) return

        if (query.isEmpty()) {
            _hideSearchResults.value = true
            _makeShadowBackground.value = false
            return
        }

        _makeShadowBackground.value = true
        viewModelScope.launch {
            _showProgressBarLoadingTracks.postValue(true)
            val albums = repository.getAlbums(query)
            if (albums?.isNotEmpty()!!) {
                _searchResults.postValue(albums)
            }
            _showProgressBarLoadingTracks.postValue(false)
        }
    }

    // Notifies about the presence or absence of an Internet connection
    private fun checkConnectionStatus(): Boolean {
        if (!connectionStatus.isOnline()) {
            _showNoInternetConnectionNotification.value = true
            return false
        }
        return true
    }
}