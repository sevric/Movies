package com.severin.movies.presentation.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.*
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.severin.movies.R
import com.severin.movies.data.model.MovieItemApi
import com.severin.movies.databinding.FragmentMovieBinding
import com.severin.movies.presentation.MovieApplicationGlobal
import com.severin.movies.presentation.vm.FavouriteMoviesViewModel
import com.severin.movies.presentation.vm.MovieByIdFromApiViewModel
import com.severin.movies.presentation.vm.MoviesViewModelFactory
import com.severin.movies.presentation.vm.WatchLaterMoviesViewModel
import com.severin.movies.utils.ConstantSet
import com.severin.movies.utils.UtilFunctions
import javax.inject.Inject

class MovieFragment : Fragment() {
    private var movieId: Int? = null

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        (requireActivity().application as MovieApplicationGlobal).component
    }

    @Inject
    lateinit var viewModelFactory: MoviesViewModelFactory
    private val favouriteMoviesViewModel: FavouriteMoviesViewModel by activityViewModels {
        viewModelFactory
    }
    private val watchLaterMoviesViewModel: WatchLaterMoviesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[WatchLaterMoviesViewModel::class.java]
    }
    private val movieByIdFromApiViewModel: MovieByIdFromApiViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MovieByIdFromApiViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

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

        movieByIdFromApiViewModel.getMovieById(movieId)
        observeOpenedMovieById()

        favouriteMoviesViewModel.getFavouriteMovieById(movieId)
        observeIsMovieFavourite()
        setClickListenerOnAddToFavourites()

        watchLaterMoviesViewModel.getWatchLaterMovieById(movieId)
        observeIsMovieWatchLater()
        setClickListenerOnAddToWatchLater()

        setYouTubeClickListener()

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

    private fun observeOpenedMovieById() {
        movieByIdFromApiViewModel.movieById.observe(viewLifecycleOwner) {
            putImage(it)
            putText(it)
        }
    }

    private fun observeIsMovieFavourite() {
        favouriteMoviesViewModel.movieFavouriteById.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.btnAddToFavorites.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.favorite_filled
                    )
                )

                Toast.makeText(
                    requireActivity(),
                    SAVED_TO_FAVOURITES_NOTIFICATION_STRING,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                binding.btnAddToFavorites.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.favorite_border
                    )
                )

                Toast.makeText(
                    requireActivity(),
                    REMOVED_FROM_FAVOURITES_NOTIFICATION_STRING,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun observeIsMovieWatchLater() {
        watchLaterMoviesViewModel.movieWatchLaterById.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.btnAddToWatchLater2.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.bookmark_filled
                    )
                )

                Toast.makeText(
                    requireActivity(),
                    SAVED_TO_WATCH_LATER_NOTIFICATION_STRING,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                binding.btnAddToWatchLater2.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.bookmark_border
                    )
                )

                Toast.makeText(
                    requireActivity(),
                    REMOVED_FROM_WATCH_LATER_NOTIFICATION_STRING,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setClickListenerOnAddToFavourites() {
        binding.btnAddToFavorites.setOnClickListener {
            val movieItemFromApi = movieByIdFromApiViewModel.movieById.value
            if (movieItemFromApi != null) {
                favouriteMoviesViewModel.toggleFavourite(movieItemFromApi)
            }
        }
    }

    private fun setClickListenerOnAddToWatchLater() {
        binding.btnAddToWatchLater2.setOnClickListener {
            val movieItemFromApi = movieByIdFromApiViewModel.movieById.value
            if (movieItemFromApi != null) {
                watchLaterMoviesViewModel.toggleWatchLaterMovie(movieItemFromApi)
            }
        }
    }

    private fun putImage(movieItemApi: MovieItemApi) {
        val imageUrl = ConstantSet.API_IMAGE_BASE_URL_AND_FILE_SIZE + movieItemApi.backdrop_path

        Glide.with(this)
            .load(imageUrl)
            .into(binding.ivPoster)
    }

    private fun putText(movieItemApi: MovieItemApi) {
        binding.titleTextView.text = movieItemApi.title
        binding.tvRatingValue.text =
            UtilFunctions.getRoundedDouble(
                movieItemApi.vote_average ?: MOVIE_DEFAULT_VOTE_AVERAGE
            ).toString()
        binding.tvYearOfIssueValue.text = movieItemApi.release_date
        binding.tvGenreValue.text = UtilFunctions.getGenresString(movieItemApi)
        binding.tvOverviewValue.text = movieItemApi.overview
    }

    private fun setYouTubeClickListener() {
        binding.btnIvYoutubeTrailerLink.setOnClickListener {
            val movieTitleString = movieByIdFromApiViewModel.movieById.value?.title
            val youtubeSearchQuery: String = movieTitleString + QUERY_END_PART
            val appIntent = Intent(
                Intent.ACTION_SEARCH,
                Uri.parse("vnd.youtube:$youtubeSearchQuery")
            )
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    String.format(YOUTUBE_SEARCH_PARAMETRIZED_LINK, youtubeSearchQuery)
                )
            )
            try {
                requireActivity().startActivity(appIntent)
            } catch (e: ActivityNotFoundException) {
                requireActivity().startActivity(webIntent)
            }
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
        private const val SAVED_TO_FAVOURITES_NOTIFICATION_STRING = "Saved to favorites"
        private const val REMOVED_FROM_FAVOURITES_NOTIFICATION_STRING = "Removed from favorites"
        private const val SAVED_TO_WATCH_LATER_NOTIFICATION_STRING = "Saved to watch later list"
        private const val REMOVED_FROM_WATCH_LATER_NOTIFICATION_STRING =
            "Removed from watch later list"
        private const val MOVIE_DEFAULT_VOTE_AVERAGE = 0.0
        private const val QUERY_END_PART = "+trailer"
        private const val YOUTUBE_SEARCH_PARAMETRIZED_LINK =
            "https://www.youtube.com/results?search_query=%1\$s"

        fun newInstance(movieId: Int) =
            MovieFragment().apply {
                arguments = Bundle().apply {
                    putInt(MOVIE_ID_KEY, movieId)
                }
            }
    }
}