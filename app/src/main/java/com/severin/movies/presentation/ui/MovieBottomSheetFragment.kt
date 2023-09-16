package com.severin.movies.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.severin.movies.databinding.FragmentMovieBottomSheetBinding

class MovieBottomSheetFragment : BottomSheetDialogFragment() {

    private var movieIdParam: Int? = null

    private var _binding: FragmentMovieBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parseArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun parseArguments() {
        val args = arguments
        if (args == null) {
            Toast.makeText(
                requireContext(),
                NO_DATA_PASSED_TOAST,
                Toast.LENGTH_SHORT
            ).show()//TODO(replace the Toast by an appearing an AlertFragment)
            activity?.supportFragmentManager?.popBackStack()//TODO(make the fragment close and back to the previous frag)
        } else if (!args.containsKey("movie_id")) {
            Toast.makeText(
                requireContext(),
                NO_MOVIE_FOUND_BY_ID_TOAST,
                Toast.LENGTH_SHORT
            ).show()//TODO(replace the Toast by an appearing an AlertFragment)
            activity?.supportFragmentManager?.popBackStack()
            //TODO(make the fragment close and back to the previous frag)
        } else {
            movieIdParam = args.getInt(MOVIE_ID_KEY)
        }
    }

    companion object {
        const val MOVIE_ID_KEY = "movie_id"
        private const val NO_DATA_PASSED_TOAST = "Can't open movie, not enough data on its id"
        private const val NO_MOVIE_FOUND_BY_ID_TOAST =
            "There was no movie found by the submitted id"

        @JvmStatic
        fun newInstance(movieId: Int) =
            MovieBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putInt(MOVIE_ID_KEY, movieId)
                }
            }
    }
}