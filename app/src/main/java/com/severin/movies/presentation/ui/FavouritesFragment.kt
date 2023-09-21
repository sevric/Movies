package com.severin.movies.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.severin.movies.data.model.MovieItemDB
import com.severin.movies.databinding.FragmentFavouritesBinding
import com.severin.movies.presentation.MovieApplicationGlobal
import com.severin.movies.presentation.adapters.MovieFragmentStarter
import com.severin.movies.presentation.adapters.MovieFromDBAdapterClickListener
import com.severin.movies.presentation.adapters.MovieFromDbAdapter
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

    private val movieFromDBAdapterClickListener by lazy {
        object : MovieFromDBAdapterClickListener {
            override fun onClick(movieItemDB: MovieItemDB) {
                MovieFragmentStarter(
                    this@FavouritesFragment
                ).startMovieFragment(movieItemDB.movieDbId)
            }

            override fun onLongClick(movieItemDB: MovieItemDB) {
                MovieFragmentStarter(
                    this@FavouritesFragment
                ).startMovieBottomSheetFragment(movieItemDB.movieDbId)
            }
        }
    }
    private val favouriteMoviesAdapter by lazy {
        MovieFromDbAdapter(movieFromDBAdapterClickListener)
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

        preparePopularMoviesAdapter()
        favouriteMoviesViewModel.getAllFavouriteMovies()
        observeViewModelFavouriteMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun preparePopularMoviesAdapter() {
        binding.rvFavourites.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                SPAN_COUNT_FOR_GRID_ADAPTER
            )
            adapter = favouriteMoviesAdapter
        }
    }

    private fun observeViewModelFavouriteMovies() {
        favouriteMoviesViewModel.allFavouriteMovies.observe(viewLifecycleOwner) {
            favouriteMoviesAdapter.submitList(it)
        }
    }

    companion object {
        private const val SPAN_COUNT_FOR_GRID_ADAPTER = 2
    }
}