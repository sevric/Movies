package com.severin.movies.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.severin.movies.databinding.ActivityMainBinding
import com.severin.movies.presentation.MovieApplicationGlobal

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as MovieApplicationGlobal).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}