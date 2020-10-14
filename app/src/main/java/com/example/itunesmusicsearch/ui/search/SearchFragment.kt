package com.example.itunesmusicsearch.ui.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.itunesmusicsearch.R
import com.example.itunesmusicsearch.data.database.Album
import com.example.itunesmusicsearch.databinding.FragmentSearchBinding
import com.example.itunesmusicsearch.di.DaggerSearchFragmentComponent
import com.example.itunesmusicsearch.di.SearchFragmentModule
import com.example.itunesmusicsearch.ui.search.adapters.AlbumsListAdapter
import com.example.itunesmusicsearch.ui.search.adapters.SearchHistoryAlbumsListAdapter
import com.example.itunesmusicsearch.ui.search.adapters.SearchResultsAlbumsListAdapter
import com.example.itunesmusicsearch.utility.hideSystemKeyboard
import com.example.itunesmusicsearch.utility.makeShadowBackGround
import javax.inject.Inject

class SearchFragment : Fragment() {
    @Inject
    lateinit var viewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchHistoryAdapter: AlbumsListAdapter
    private lateinit var searchResultsAdapter: AlbumsListAdapter


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("TAG", "Details onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG", "Search onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injectViewModel()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.lifecycleOwner = this
        initSearchHistoryList()
        initSearchResultsList()
        setViewModelObservers()
        setSearchView()
        Log.d("TAG", "Search onCreateView")
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("TAG", "Search onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG", "Search onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "Search onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TAG", "Search onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG", "Search onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TAG", "Search onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "Search onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("TAG", "Search onDetach")
    }


    private fun injectViewModel() {
        val component = DaggerSearchFragmentComponent.builder()
            .searchFragmentModule(SearchFragmentModule(requireContext()))
            .build()
        component.inject(this)
    }

    private fun initSearchHistoryList() {
        searchHistoryAdapter = SearchHistoryAlbumsListAdapter(viewModel)
        binding.listSearchHistory.adapter = searchHistoryAdapter
    }

    private fun initSearchResultsList() {
        searchResultsAdapter = SearchResultsAlbumsListAdapter(viewModel)
        binding.listSearchResults.adapter = searchResultsAdapter
        binding.listSearchResults.setHasFixedSize(true)
    }

    private fun setViewModelObservers() {
        viewModel.searchHistory.observe(viewLifecycleOwner, Observer { albums ->
            if (albums.isNotEmpty()) refreshSearchHistory(albums)
        })

        viewModel.searchResults.observe(viewLifecycleOwner, Observer { albums ->
            refreshSearchResults(albums)
        })

        viewModel.makeShadowBackground.observe(viewLifecycleOwner, Observer { event ->
            binding.viewShadow.makeShadowBackGround(event)
        })

        viewModel.showProgressBarLoadingTracks.observe(viewLifecycleOwner, Observer { event ->
            if (event) binding.progressLoadingTracks.visibility = View.VISIBLE
            else binding.progressLoadingTracks.visibility = View.GONE
        })

        viewModel.showNoInternetConnectionMessage.observe(viewLifecycleOwner, Observer { event ->
            if (event) showNoInternetConnectionMessage()
        })

        viewModel.hideSearchResults.observe(viewLifecycleOwner, Observer { event ->
            if (event) binding.listSearchResults.visibility = View.GONE
        })

        viewModel.clearSearchQuery.observe(viewLifecycleOwner, Observer { event ->
            if (event) binding.editSearch.text?.clear()
        })

        viewModel.hideKeyBoard.observe(viewLifecycleOwner, Observer { event ->
            if (event) binding.root.hideSystemKeyboard()
        })

        viewModel.navigateToAlbumDetails.observe(viewLifecycleOwner, Observer { album ->
            navigateToAlbumDetailsFragment(album)
        })
    }

    private fun setSearchView() {
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(query: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onSearchQueryTextChanged(query.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun refreshSearchHistory(albums: List<Album>) {
        searchHistoryAdapter.refreshAlbums(albums)
        binding.apply {
            textNoSearchHistory.visibility = View.GONE
            listSearchHistory.visibility = View.VISIBLE
        }
    }

    private fun refreshSearchResults(albums: List<Album>) {
        binding.listSearchResults.apply {
            visibility = View.VISIBLE
            layoutManager?.scrollToPosition(0)
        }
        searchResultsAdapter.refreshAlbums(albums)
    }

    private fun showNoInternetConnectionMessage() {
        Toast.makeText(
            requireContext(),
            getString(R.string.show_no_internet_connection),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun navigateToAlbumDetailsFragment(album: Album) {
        val bundle = Bundle().apply { putSerializable(Album.SERIALIZABLE_TAG, album) }
        if (findNavController().currentDestination?.id == R.id.searchFragment) {
            findNavController().navigate(R.id.action_searchFragment_to_albumDetailsFragment, bundle)
        }
    }
}