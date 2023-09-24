package com.severin.movies.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
import com.severin.movies.utils.UtilFunctions
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        (requireActivity().application as MovieApplicationGlobal).component
    }

    @Inject
    lateinit var viewModelFactory: MoviesViewModelFactory
    private val mostPopularViewModel: MostPopularMoviesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MostPopularMoviesViewModel::class.java]
    }
    private val nowInTheTheatersViewModel: NowInTheTheatersViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[NowInTheTheatersViewModel::class.java]
    }
    private val highlightedByPeriodViewModel: HighlightedByPeriodViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HighlightedByPeriodViewModel::class.java]
    }
    private val moviesForChildrenViewModel: MoviesForChildrenViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MoviesForChildrenViewModel::class.java]
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

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
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

        prepareHighlightedPeriodSpinner()
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

    private fun prepareHighlightedPeriodSpinner() {
        //getting spinner from the view
        val spinner: Spinner = binding.spinnerTimePeriod

        //setting spinner: layouts for it and then adapter
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.periods_array,
            R.layout.spinner_item_custom
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        //setting clickListener for the spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                pos: Int,
                id: Long
            ) {
                // An item was selected. You can retrieve the selected item using
                val selectedPeriodName: String = parent.getItemAtPosition(pos).toString()
                val period = UtilFunctions.choosePeriod(selectedPeriodName, requireActivity())
                highlightedByPeriodViewModel.getHighlightedByPeriodMovies(period)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
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