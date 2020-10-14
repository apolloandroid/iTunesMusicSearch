package com.example.itunesmusicsearch.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itunesmusicsearch.data.Repository
import com.example.itunesmusicsearch.utility.ConnectionStatus

class SearchViewModelFactory(
    private val repository: Repository,
    private val connectionStatus: ConnectionStatus
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository, connectionStatus) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}