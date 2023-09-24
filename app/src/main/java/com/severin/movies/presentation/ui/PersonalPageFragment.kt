package com.severin.movies.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.severin.movies.R
import com.severin.movies.data.model.MovieItemDB
import com.severin.movies.databinding.FragmentPersonalPageBinding
import com.severin.movies.presentation.MovieApplicationGlobal
import com.severin.movies.presentation.adapters.MovieFragmentStarter
import com.severin.movies.presentation.adapters.MovieFromDBAdapterClickListener
import com.severin.movies.presentation.adapters.MovieFromDbAdapter
import com.severin.movies.presentation.vm.FirebaseAuthorizationViewModel
import com.severin.movies.presentation.vm.FirebaseDBViewModel

class PersonalPageFragment : Fragment() {

    private var _binding: FragmentPersonalPageBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        (requireActivity().application as MovieApplicationGlobal).component
    }

    private val firebaseAuthorizationViewModel: FirebaseAuthorizationViewModel by lazy {
        ViewModelProvider(
            requireActivity()
        )[FirebaseAuthorizationViewModel::class.java]
    }
    private val firebaseDBViewModel: FirebaseDBViewModel by lazy {
        ViewModelProvider(
            requireActivity()
        )[FirebaseDBViewModel::class.java]
    }

    private val movieFromDBAdapterClickListener by lazy {
        object : MovieFromDBAdapterClickListener {
            override fun onClick(movieItemDB: MovieItemDB) {//TODO(not as by clean architecture, maybe use interface inside domain layer)
                MovieFragmentStarter(
                    this@PersonalPageFragment
                ).startMovieFragment(movieItemDB.movieDbId)//TODO(not as by clean architecture, maybe use interface inside domain layer)
            }

            override fun onLongClick(movieItemDB: MovieItemDB) {
                MovieFragmentStarter(
                    this@PersonalPageFragment
                ).startMovieBottomSheetFragment(movieItemDB.movieDbId)
            }
        }
    }
    private val watchLaterMoviesAdapter by lazy {
        MovieFromDbAdapter(movieFromDBAdapterClickListener)
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonalPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeFirebaseAuthorizationViewModel()
        prepareClickListener()
        prepareAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeFirebaseAuthorizationViewModel() {
        firebaseAuthorizationViewModel.currentFirebaseAuthUser.observe(this) {
            if (it == null) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.main_navigation_view,
                        LoginFragment.newInstance(DEFAULT_EMPTY_EMAIL_STRING)
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }

        firebaseDBViewModel.currentFirebaseDBUser.observe(this) {
            if (it != null) {
                binding.tvNameAndSurname.text = String.format(
                    STRING_WITH_NAME_AND_LAST_NAME_TO_FORMAT,
                    it.name,
                    it.lastName
                )
            }
        }

        //TODO(make adding watch later movies from the firebase DB)
    }

    private fun prepareClickListener() {
        binding.ivBtnLogoutHidden.setOnClickListener {//TODO(change to other btn (hidden in the menu by click on the current btn))
            firebaseAuthorizationViewModel.logout()
        }
    }

    private fun prepareAdapter() {
        binding.rvAccountWatchLater.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                SPAN_COUNT_FOR_GRID_ADAPTER
            )
            adapter = watchLaterMoviesAdapter
        }
    }

    companion object {
        private const val DEFAULT_EMPTY_EMAIL_STRING = ""
        private const val STRING_WITH_NAME_AND_LAST_NAME_TO_FORMAT = "%s %s"
        private const val SPAN_COUNT_FOR_GRID_ADAPTER = 2

        @JvmStatic
        fun newInstance() =
            PersonalPageFragment()
    }
}