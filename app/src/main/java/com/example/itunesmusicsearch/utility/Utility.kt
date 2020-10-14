package com.example.itunesmusicsearch.utility

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.ViewCompat

// File with helper classes and extension functions

// Defines status of Internet connection
class ConnectionStatus(private val context: Context) {
    fun isOnline(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo ?: return false
        return networkInfo.isConnected
    }
}

// Hides system keyboard
fun View.hideSystemKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

// Hides/shows shadow at during searching albums
fun View.makeShadowBackGround(event: Boolean) {
    visibility = when (event) {
        true -> View.VISIBLE
        false -> View.GONE
    }
    ViewCompat.animate(this).alpha(1f).setDuration(500).start()
}