package com.severin.movies.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.severin.movies.R
import com.severin.movies.databinding.FragmentHomeBinding
import com.severin.movies.presentation.MovieApplicationGlobal
import com.severin.movies.presentation.vm.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val movieApplicationGlobal: MovieApplicationGlobal by lazy {
        MovieApplicationGlobal.instance
    }
    private val mostPopularViewModel: MostPopularMoviesViewModel by viewModels {
        MoviesViewModelFactory(movieApplicationGlobal)
    }
    private val nowInTheTheatersViewModel: NowInTheTheatersViewModel by viewModels {
        MoviesViewModelFactory(movieApplicationGlobal)
    }
    private val highlightedByPeriodViewModel: HighlightedByPeriodViewModel by viewModels {
        MoviesViewModelFactory(movieApplicationGlobal)
    }
    private val moviesForChildrenViewModel: MoviesForChildrenViewModel by viewModels {
        MoviesViewModelFactory(movieApplicationGlobal)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareClickListener()

        mostPopularViewModel.getPopularMoviesFromApi()
        observeViewModelPopularMovies()

        nowInTheTheatersViewModel.getNowInTheTheatersMovies()
        observeNowInTheTheatersViewModel()

        //TODO(call data for the highlightedByPeriodViewModel Live Data from the spinner in the subsequent)
        observeHighlightedPeriodViewModel()

        moviesForChildrenViewModel.getMoviesForKidsFromApi()
        observeMoviesForChildrenViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun prepareClickListener() {
        binding.ivBtnStartSearching.setOnClickListener {
            val queryString = binding.etEnterSearchQuery.text.toString()

            requireActivity().supportFragmentManager.beginTransaction()
                .add(
                    R.id.navigation_fragment,
                    MoviesByUserSearchQueryFragment.newInstance(queryString)
                )
                .addToBackStack(null)
                .commit()
        }
    }

    private fun observeViewModelPopularMovies() {
        mostPopularViewModel.popularMoviesLiveData.observe(viewLifecycleOwner) {
            //TODO(submitList(it.results) for most popular movies Adapter)
        }
    }

    private fun observeNowInTheTheatersViewModel() {
        nowInTheTheatersViewModel.nowInTheTheatersLiveData.observe(viewLifecycleOwner) {
            //TODO(submitList(it.results) for now in the theaters movies Adapter)
        }
    }

    private fun observeHighlightedPeriodViewModel() {
        highlightedByPeriodViewModel.highlightedByPeriodLiveData.observe(viewLifecycleOwner) {
            //TODO(submitList(it.results) for highlighted period movies Adapter)
        }
    }

    private fun observeMoviesForChildrenViewModel() {
        moviesForChildrenViewModel.moviesForKidsFromApi.observe(viewLifecycleOwner) {
            //TODO(submitList(it.results) for movies for children Adapter)
        }
    }
}