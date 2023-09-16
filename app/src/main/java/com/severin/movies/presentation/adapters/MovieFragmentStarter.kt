package com.severin.movies.presentation.adapters

import androidx.fragment.app.Fragment
import com.severin.movies.R
import com.severin.movies.presentation.ui.MovieBottomSheetFragment
import com.severin.movies.presentation.ui.MovieFragment
import com.severin.movies.presentation.ui.MoviesByGenreFragment

class MovieFragmentStarter(
    private val initiatorFragment: Fragment
) {
    fun startMovieFragment(movieId: Int?) {
        if (movieId == null) {
            //TODO(make Alert Message)
        } else {
            initiatorFragment.requireActivity().supportFragmentManager.beginTransaction()
                .add(
                    R.id.navigation_fragment,
                    MovieFragment.newInstance(movieId)
                )
                .addToBackStack(null)
                .commit()
        }
    }

    fun startMoviesByGenreFragment(genreId: Int?, genreName: String?) {
        if (genreId == null) {
            //TODO(make Alert Message)
        } else if (genreName == null) {
            //TODO(make Alert Message about "unknown genre name by the id received")
        } else {
            initiatorFragment.requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    R.id.navigation_fragment,
                    MoviesByGenreFragment.newInstance(genreId, genreName)
                )
                .addToBackStack(null)
                .commit()
        }
    }

    fun startMovieBottomSheetFragment(movieId: Int?) {
        if (movieId != null) {
            val initiatorFragmentChildManager = initiatorFragment.childFragmentManager
            MovieBottomSheetFragment.newInstance(movieId)
                .show(initiatorFragmentChildManager, FRAGMENT_TRANSACTION_TAG)
        }
    }

    companion object {
        private const val FRAGMENT_TRANSACTION_TAG = "Movie_short_info"
    }
}