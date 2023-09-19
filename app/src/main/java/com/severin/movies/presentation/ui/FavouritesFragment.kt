package com.severin.movies.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.severin.movies.databinding.FragmentFavouritesBinding
import com.severin.movies.presentation.MovieApplicationGlobal
import com.severin.movies.presentation.vm.FavouriteMoviesViewModel
import com.severin.movies.presentation.vm.MovieByIdFromApiViewModel
import com.severin.movies.presentation.vm.MoviesViewModelFactory

class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private val movieApplicationGlobal: MovieApplicationGlobal by lazy {
        MovieApplicationGlobal.instance
    }
    private val favouriteMoviesViewModel: FavouriteMoviesViewModel by activityViewModels {
        MoviesViewModelFactory(movieApplicationGlobal)
    }
    private val movieByIdFromApiViewModel: MovieByIdFromApiViewModel by viewModels {
        MoviesViewModelFactory(movieApplicationGlobal)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouriteMoviesViewModel.getAllFavouriteMovies()
        observeViewModelFavouriteMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModelFavouriteMovies() {
        favouriteMoviesViewModel.allFavouriteMovies.observe(viewLifecycleOwner) {
            //TODO(submitList(it) for favourite movies Adapter)
        }
    }
}