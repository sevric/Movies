package com.severin.movies.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.severin.movies.R
import com.severin.movies.data.model.MovieItemApi
import com.severin.movies.databinding.FragmentMoviesByUserSearchQueryBinding
import com.severin.movies.presentation.MovieApplicationGlobal
import com.severin.movies.presentation.adapters.MovieFragmentStarter
import com.severin.movies.presentation.adapters.MovieFromApiAdapterClickListener
import com.severin.movies.presentation.adapters.MoviesFromApiGridAdapter
import com.severin.movies.presentation.vm.MoviesByUserSearchQueryViewModel
import com.severin.movies.presentation.vm.MoviesViewModelFactory
import javax.inject.Inject

class MoviesByUserSearchQueryFragment : Fragment() {
    private var queryStringParam: String? = null

    private var _binding: FragmentMoviesByUserSearchQueryBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        (requireActivity().application as MovieApplicationGlobal).component
    }

    @Inject
    lateinit var viewModelFactory: MoviesViewModelFactory
    private val moviesByUserSearchQueryViewModel: MoviesByUserSearchQueryViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[MoviesByUserSearchQueryViewModel::class.java]
    }

    private val movieBySearchQueryAdapterClickListener by lazy {
        object : MovieFromApiAdapterClickListener {
            override fun onClick(movieItemApi: MovieItemApi) {
                MovieFragmentStarter(
                    this@MoviesByUserSearchQueryFragment
                ).startMovieFragment(movieItemApi.id)
            }

            override fun onLongClick(movieItemApi: MovieItemApi) {
                MovieFragmentStarter(
                    this@MoviesByUserSearchQueryFragment
                ).startMovieBottomSheetFragment(movieItemApi.id)
            }
        }
    }
    private val searchQueryMoviesAdapter by lazy {
        MoviesFromApiGridAdapter(movieBySearchQueryAdapterClickListener)
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parseArguments()
        forceHideKeyboard()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesByUserSearchQueryBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        putText()
        preparePopularMoviesAdapter()
        moviesByUserSearchQueryViewModel.getMoviesByUserSearchQuery(queryStringParam)
        observeViewModelPopularMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArguments() {
        val args = arguments
        if (args == null) {
            Toast.makeText(
                this.requireContext(),
                NO_QUERY_ENTERED_NOTIFICATION,
                Toast.LENGTH_SHORT
            ).show()//TODO(replace the Toast by an appearing an AlertFragment)
            activity?.supportFragmentManager?.popBackStack()//TODO(make the fragment close and back to the previous frag)
        } else if (!(args.containsKey(QUERY_STRING_PARAM))) {
            Toast.makeText(
                this.requireContext(),
                REQUEST_ENTER_QUERY_NOTIFICATION,
                Toast.LENGTH_SHORT
            ).show()//TODO(replace the Toast by an appearing an AlertFragment)
            activity?.supportFragmentManager?.popBackStack()
            //TODO(make the fragment close and back to the previous frag)
        } else {
            queryStringParam = args.getString(QUERY_STRING_PARAM)
        }
    }

    private fun forceHideKeyboard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = requireActivity().currentFocus
        if (view != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun putText() {
        val stringResource: String = resources.getString(R.string.title_movies_by_query)
        binding.tvSearchResultsTitle.text = String.format(stringResource, queryStringParam)
    }

    private fun preparePopularMoviesAdapter() {
        binding.rvMoviesBySearchQuery.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                SPAN_COUNT_FOR_GRID_ADAPTER
            )
            adapter = searchQueryMoviesAdapter
        }
    }

    private fun observeViewModelPopularMovies() {
        moviesByUserSearchQueryViewModel.moviesByUserSearchQuery.observe(viewLifecycleOwner) {
            searchQueryMoviesAdapter.submitList(it.results)
        }
    }

    companion object {
        private const val QUERY_STRING_PARAM = "query_string_param"
        private const val NO_QUERY_ENTERED_NOTIFICATION = "No query were entered"
        private const val REQUEST_ENTER_QUERY_NOTIFICATION = "Enter a query"
        private const val SPAN_COUNT_FOR_GRID_ADAPTER = 2

        @JvmStatic
        fun newInstance(param1: String) =
            MoviesByUserSearchQueryFragment().apply {
                arguments = Bundle().apply {
                    putString(QUERY_STRING_PARAM, param1)
                }
            }
    }
}