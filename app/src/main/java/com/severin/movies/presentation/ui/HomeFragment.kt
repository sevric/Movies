package com.severin.movies.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.severin.movies.R
import com.severin.movies.data.model.MovieItemApi
import com.severin.movies.databinding.FragmentHomeBinding
import com.severin.movies.presentation.MovieApplicationGlobal
import com.severin.movies.presentation.adapters.MovieFragmentStarter
import com.severin.movies.presentation.adapters.MovieFromApiAdapterClickListener
import com.severin.movies.presentation.adapters.MoviesFromApiLinearAdapter
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

    private val movieFromApiAdapterClickListener by lazy {
        object : MovieFromApiAdapterClickListener {
            override fun onClick(movieItemApi: MovieItemApi) {
                MovieFragmentStarter(
                    this@HomeFragment
                ).startMovieFragment(movieItemApi.id)
            }

            override fun onLongClick(movieItemApi: MovieItemApi) {
                MovieFragmentStarter(
                    this@HomeFragment
                ).startMovieBottomSheetFragment(movieItemApi.id)
            }
        }
    }
    private val mostPopularMoviesAdapter by lazy {
        MoviesFromApiLinearAdapter(movieFromApiAdapterClickListener)
    }
    private val nowInTheTheatersMoviesAdapter by lazy {
        MoviesFromApiLinearAdapter(movieFromApiAdapterClickListener)
    }
    private val highlightedPeriodMoviesAdapter by lazy {
        MoviesFromApiLinearAdapter(movieFromApiAdapterClickListener)
    }
    private val moviesForChildrenAdapter by lazy {
        MoviesFromApiLinearAdapter(movieFromApiAdapterClickListener)
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

        preparePopularMoviesAdapter()
        mostPopularViewModel.getPopularMoviesFromApi()
        observeViewModelPopularMovies()

        prepareNowInTheTheatersAdapter()
        nowInTheTheatersViewModel.getNowInTheTheatersMovies()
        observeNowInTheTheatersViewModel()

        //TODO(call data for the highlightedByPeriodViewModel Live Data from the spinner in the subsequent)
        prepareHighlightedPeriodAdapter()
        observeHighlightedPeriodViewModel()

        prepareMoviesForChildrenAdapter()
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

    private fun preparePopularMoviesAdapter() {
        binding.rvMostPopularMovies.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                SPAN_COUNT_FOR_GRID_ADAPTER
            )
            adapter = mostPopularMoviesAdapter
        }
    }

    private fun prepareNowInTheTheatersAdapter() {
        binding.rvNowInTheTheaters.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = nowInTheTheatersMoviesAdapter//the same instance of adapter
        }
    }

    private fun prepareMoviesForChildrenAdapter() {
        binding.rvForKids.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = moviesForChildrenAdapter
        }
    }

    private fun prepareHighlightedPeriodAdapter() {
        binding.rvHighlightedPeriod.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = highlightedPeriodMoviesAdapter
        }
    }

    private fun observeViewModelPopularMovies() {
        mostPopularViewModel.popularMoviesLiveData.observe(viewLifecycleOwner) {
            mostPopularMoviesAdapter.submitList(it.results)
        }
    }

    private fun observeNowInTheTheatersViewModel() {
        nowInTheTheatersViewModel.nowInTheTheatersLiveData.observe(viewLifecycleOwner) {
            nowInTheTheatersMoviesAdapter.submitList(it.results)
        }
    }

    private fun observeHighlightedPeriodViewModel() {
        highlightedByPeriodViewModel.highlightedByPeriodLiveData.observe(viewLifecycleOwner) {
            highlightedPeriodMoviesAdapter.submitList(it.results)
        }
    }

    private fun observeMoviesForChildrenViewModel() {
        moviesForChildrenViewModel.moviesForKidsFromApi.observe(viewLifecycleOwner) {
            moviesForChildrenAdapter.submitList(it.results)
        }
    }

    companion object {
        private const val SPAN_COUNT_FOR_GRID_ADAPTER = 2
    }
}