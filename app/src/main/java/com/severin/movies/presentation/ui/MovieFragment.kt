package com.severin.movies.presentation.ui

import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.*
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.severin.movies.databinding.FragmentMovieBinding

class MovieFragment : Fragment() {
    private var movieId: Int? = null

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parseArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addCollapsingLayoutListener()
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
                NO_MOVIE_DATA_TEXT,
                Toast.LENGTH_SHORT
            ).show()//TODO(replace the Toast by an appearing an AlertFragment)
            activity?.supportFragmentManager?.popBackStack()//TODO(make the fragment close and back to the previous frag)
        } else if (!(args.containsKey(MOVIE_ID_KEY))) {
            Toast.makeText(
                this.requireContext(),
                NO_MOVIE_FOUND_BY_ID,
                Toast.LENGTH_SHORT
            ).show()//TODO(replace the Toast by an appearing an AlertFragment)
            activity?.supportFragmentManager?.popBackStack()
            //TODO(make the fragment close and back to the previous frag)
        } else {
            movieId = args.getInt(MOVIE_ID_KEY)
        }
    }

    private fun addCollapsingLayoutListener() {
        val titleMaxSize: Float = binding.collapsingToolBar.expandedTitleTextSize
        val titleMinSize: Float = binding.collapsingToolBar.collapsedTitleTextSize
        val titleMarginStartMax: Int = binding.collapsingToolBar.expandedTitleMarginStart
        val titleMarginStartMin: Int = binding.collapsingToolBar.collapsedTitleTextSize.toInt()
        val titleMarginBottomMax: Int = binding.collapsingToolBar.expandedTitleMarginBottom
        val titleMarginBottomMin: Int = binding.collapsingToolBar.collapsedTitleTextSize.toInt()

        val appBarLayout = binding.appBarLayout
        appBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
            val totalScrollRange = appBarLayout.totalScrollRange
            val collapsingRatio = -verticalOffset.toFloat() / totalScrollRange.toFloat()

            animateTitle(
                collapsingRatio,
                titleMaxSize,
                titleMinSize,
                titleMarginStartMax,
                titleMarginStartMin,
                titleMarginBottomMax,
                titleMarginBottomMin
            )
        }
    }

    private fun animateTitle(
        collapsingRatio: Float,
        titleMaxSize: Float,
        titleMinSize: Float,
        titleMarginStartMax: Int,
        titleMarginStartMin: Int,
        titleMarginBottomMax: Int,
        titleMarginBottomMin: Int
    ) {
        val textSizeDiff = titleMaxSize - titleMinSize
        val newTextSize = titleMaxSize - (textSizeDiff * collapsingRatio)

        val marginStartDiff = titleMarginStartMax - titleMarginStartMin
        val newMarginStart = titleMarginStartMax - (marginStartDiff * collapsingRatio)

        binding.titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, newTextSize)

        val marginBottomDiff = titleMarginBottomMax - titleMarginBottomMin
        val newMarginBottom = titleMarginBottomMax - (marginBottomDiff * collapsingRatio)

        val params = CollapsingToolbarLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.BOTTOM
        params.marginStart = newMarginStart.toInt()
        params.bottomMargin = newMarginBottom.toInt()
        binding.titleTextView.layoutParams = params
    }

    companion object {
        const val MOVIE_ID_KEY = "movie_id"
        private const val NO_MOVIE_DATA_TEXT = "Can't open movie, not enough data on its id"
        private const val NO_MOVIE_FOUND_BY_ID = "There was no movie found by the submitted id"

        fun newInstance(movieId: Int) =
            MovieFragment().apply {
                arguments = Bundle().apply {
                    putInt(MOVIE_ID_KEY, movieId)
                }
            }
    }
}