package com.severin.movies.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.severin.movies.data.model.MovieItemApi
import com.severin.movies.databinding.FragmentMovieBottomSheetBinding
import com.severin.movies.presentation.MovieApplicationGlobal
import com.severin.movies.presentation.adapters.MovieFragmentStarter
import com.severin.movies.presentation.vm.MovieByIdFromApiViewModel
import com.severin.movies.presentation.vm.MoviesViewModelFactory
import com.severin.movies.utils.ConstantSet
import com.severin.movies.utils.UtilFunctions
import com.severin.movies.utils.UtilFunctions.getGenresString
import javax.inject.Inject

class MovieBottomSheetFragment : BottomSheetDialogFragment() {

    private var movieIdParam: Int? = null

    private var _binding: FragmentMovieBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        (requireActivity().application as MovieApplicationGlobal).component
    }

    @Inject
    lateinit var viewModelFactory: MoviesViewModelFactory

    private val movieByIdFromApiViewModel:MovieByIdFromApiViewModel by lazy {
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
        _binding = FragmentMovieBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieByIdFromApiViewModel.getMovieById(movieIdParam)
        observePressedMovieById()
        prepareClickListener()
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
        } else if (!args.containsKey(MOVIE_ID_KEY)) {
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

    private fun observePressedMovieById() {
        movieByIdFromApiViewModel.movieById.observe(viewLifecycleOwner) {
            putImage(it)
            putText(it)
        }
    }

    private fun prepareClickListener() {
        binding.btnTvDetails.setOnClickListener {
            MovieFragmentStarter(this).startMovieFragment(movieIdParam)
            dismiss()
        }
    }

    private fun putImage(movieItemApi: MovieItemApi) {
        val imageUrl = ConstantSet.API_IMAGE_BASE_URL_AND_FILE_SIZE + movieItemApi.backdrop_path

        Glide.with(this)
            .load(imageUrl)
            .into(binding.ivMoviePoster)
    }

    private fun putText(movieItemApi: MovieItemApi) {
        binding.tvMovieTitle.text = movieItemApi.title

        val voteAverage = movieItemApi.vote_average
        binding.tvRatingValue.text =
            UtilFunctions.getRoundedDouble(voteAverage ?: DEFAULT_VOTE_AVERAGE_ZERO)
                .toString()

        binding.tvGenresValue.text = getGenresString(movieItemApi)
    }

    companion object {
        const val MOVIE_ID_KEY = "movie_id"
        private const val NO_DATA_PASSED_TOAST = "Can't open movie, not enough data on its id"
        private const val NO_MOVIE_FOUND_BY_ID_TOAST =
            "There was no movie found by the submitted id"
        private const val DEFAULT_VOTE_AVERAGE_ZERO = 0.0

        @JvmStatic
        fun newInstance(movieId: Int) =
            MovieBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putInt(MOVIE_ID_KEY, movieId)
                }
            }
    }
}