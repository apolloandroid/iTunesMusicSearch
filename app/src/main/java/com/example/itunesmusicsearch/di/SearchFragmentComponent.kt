package com.example.itunesmusicsearch.di

import com.example.itunesmusicsearch.ui.search.SearchFragment
import dagger.Component

// Component for injecting Search fragment
@Component(modules = [SearchFragmentModule::class])
interface SearchFragmentComponent {
    fun inject(searchFragment: SearchFragment)
}