package com.severin.movies.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.severin.movies.R
import com.severin.movies.databinding.FragmentMainNavigationBinding

class MainNavigationFragment : Fragment() {
    private var _binding: FragmentMainNavigationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainNavigationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareNavigationController()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun prepareNavigationController() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.main_navigation_view) as NavHostFragment?
        val navController = navHostFragment?.findNavController()
        val navView: BottomNavigationView = binding.bottomNavigation
        if (navController != null) {
            navView.setupWithNavController(navController)
        }
    }
}