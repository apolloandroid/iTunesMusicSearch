package com.example.itunesmusicsearch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.itunesmusicsearch.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("TAG", "Activity onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG", "Activity onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "Activity onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TAG", "Activity onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG", "Activity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "Activity onDestroy")
    }
}


