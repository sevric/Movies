package com.severin.movies.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.severin.movies.databinding.FragmentGenresBinding
import com.severin.movies.presentation.MovieApplicationGlobal
import com.severin.movies.presentation.vm.GenresViewModel
import com.severin.movies.presentation.vm.MoviesViewModelFactory

class GenresFragment : Fragment() {

    private var _binding: FragmentGenresBinding? = null
    private val binding get() = _binding!!

    private val movieApplicationGlobal: MovieApplicationGlobal by lazy {
        MovieApplicationGlobal.instance
    }
    private val genresViewModel: GenresViewModel by viewModels {
        MoviesViewModelFactory(movieApplicationGlobal)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        genresViewModel.getGenres()
        observeGenresViewModel()
    }

    private fun observeGenresViewModel() {
        genresViewModel.genresFromApi.observe(viewLifecycleOwner) {
            //TODO(submitList(it.genres) for genres Adapter)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}