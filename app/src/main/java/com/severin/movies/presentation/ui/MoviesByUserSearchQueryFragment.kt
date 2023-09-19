package com.severin.movies.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.severin.movies.R
import com.severin.movies.databinding.FragmentMoviesByUserSearchQueryBinding
import com.severin.movies.presentation.MovieApplicationGlobal
import com.severin.movies.presentation.vm.MoviesByUserSearchQueryViewModel
import com.severin.movies.presentation.vm.MoviesViewModelFactory

class MoviesByUserSearchQueryFragment : Fragment() {
    private var queryStringParam: String? = null

    private var _binding: FragmentMoviesByUserSearchQueryBinding? = null
    private val binding get() = _binding!!

    private val movieApplicationGlobal: MovieApplicationGlobal by lazy {
        MovieApplicationGlobal.instance
    }
    private val moviesByUserSearchQueryViewModel: MoviesByUserSearchQueryViewModel by viewModels {
        MoviesViewModelFactory(movieApplicationGlobal)
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
                NO_QUERY_ENTERED,
                Toast.LENGTH_SHORT
            ).show()//TODO(replace the Toast by an appearing an AlertFragment)
            activity?.supportFragmentManager?.popBackStack()//TODO(make the fragment close and back to the previous frag)
        } else if (!(args.containsKey(QUERY_STRING_PARAM))) {
            Toast.makeText(
                this.requireContext(),
                REQUEST_ENTER_QUERY,
                Toast.LENGTH_SHORT
            ).show()//TODO(replace the Toast by an appearing an AlertFragment)
            activity?.supportFragmentManager?.popBackStack()
            //TODO(make the fragment close and back to the previous frag)
        } else {
            queryStringParam = args.getString(QUERY_STRING_PARAM)
        }
    }

    private fun forceHideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = requireActivity().currentFocus
        if (view != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun putText() {
        val stringResource: String = resources.getString(R.string.title_movies_by_query)
        binding.tvSearchResultsTitle.text = String.format(stringResource, queryStringParam)
    }

    private fun observeViewModelPopularMovies() {
        moviesByUserSearchQueryViewModel.moviesByUserSearchQuery.observe(viewLifecycleOwner) {
            //TODO(submitList(it.results) for search query movies Adapter)
        }
    }

    companion object {
        private const val QUERY_STRING_PARAM = "query_string_param"
        private const val NO_QUERY_ENTERED = "No query were entered"
        private const val REQUEST_ENTER_QUERY = "Enter a query"

        @JvmStatic
        fun newInstance(param1: String) =
            MoviesByUserSearchQueryFragment().apply {
                arguments = Bundle().apply {
                    putString(QUERY_STRING_PARAM, param1)
                }
            }
    }
}