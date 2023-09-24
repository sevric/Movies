package com.severin.movies.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.severin.movies.data.model.Genre
import com.severin.movies.databinding.FragmentGenresBinding
import com.severin.movies.presentation.MovieApplicationGlobal
import com.severin.movies.presentation.adapters.GenresAdapter
import com.severin.movies.presentation.adapters.GenresAdapterClickListener
import com.severin.movies.presentation.adapters.MovieFragmentStarter
import com.severin.movies.presentation.vm.GenresViewModel
import com.severin.movies.presentation.vm.MoviesViewModelFactory
import javax.inject.Inject

class GenresFragment : Fragment() {

    private var _binding: FragmentGenresBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        (requireActivity().application as MovieApplicationGlobal).component
    }

    @Inject
    lateinit var viewModelFactory: MoviesViewModelFactory
    private val genresViewModel: GenresViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GenresViewModel::class.java]
    }

    private val genresAdapterClickListener by lazy {
        object : GenresAdapterClickListener {
            override fun onClick(genre: Genre) {
                MovieFragmentStarter(
                    this@GenresFragment
                ).startMoviesByGenreFragment(genre.id, genre.name)
            }
        }
    }
    private val genresAdapter by lazy {
        GenresAdapter(genresAdapterClickListener)
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareGenresAdapter()
        genresViewModel.getGenres()
        observeGenresViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun prepareGenresAdapter() {
        binding.rvGenres.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                SPAN_COUNT_FOR_GRID_ADAPTER
            )
            adapter = genresAdapter
        }
    }

    private fun observeGenresViewModel() {
        genresViewModel.genresFromApi.observe(viewLifecycleOwner) {
            genresAdapter.submitList(it.genres)
        }
    }

    companion object {
        private const val SPAN_COUNT_FOR_GRID_ADAPTER = 2
    }
}