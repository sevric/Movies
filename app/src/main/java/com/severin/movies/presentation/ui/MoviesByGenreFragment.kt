package com.severin.movies.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.severin.movies.databinding.FragmentMoviesByGenreBinding

class MoviesByGenreFragment : Fragment() {
    private var genreId: Int? = null
    private var genreName: String? = null

    private var _binding: FragmentMoviesByGenreBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parseArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesByGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setGenreTitle()
    }

    private fun setGenreTitle() {
        binding.tvGenreName.text = genreName
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
                NO_DATA_OR_GENRE_ID,
                Toast.LENGTH_SHORT
            ).show()//TODO(replace the Toast by an appearing an AlertFragment)
            activity?.supportFragmentManager?.popBackStack()//TODO(make the fragment close and back to the previous frag)
        } else if (!(args.containsKey(GENRE_ID_KEY)) || !(args.containsKey(GENRE_NAME_KEY))) {
            Toast.makeText(
                this.requireContext(),
                NO_MOVIES_BY_ID_OR_NAME,
                Toast.LENGTH_SHORT
            ).show()//TODO(replace the Toast by an appearing an AlertFragment)
            activity?.supportFragmentManager?.popBackStack()
            //TODO(make the fragment close and back to the previous frag)
        } else {
            genreId = args.getInt(GENRE_ID_KEY)
            genreName = args.getString(GENRE_NAME_KEY)
        }
    }

    companion object {
        const val GENRE_ID_KEY = "genre_id"
        const val GENRE_NAME_KEY = "genre_name"
        private const val NO_DATA_OR_GENRE_ID = "Can't open movies, not enough data on genre id"
        private const val NO_MOVIES_BY_ID_OR_NAME =
            "There were no movies found by the submitted genre id or with the submitted name"

        fun newInstance(genreId: Int, genreName: String) =
            MoviesByGenreFragment().apply {
                arguments = Bundle().apply {
                    putInt(GENRE_ID_KEY, genreId)
                    putString(GENRE_NAME_KEY, genreName)
                }
            }
    }
}