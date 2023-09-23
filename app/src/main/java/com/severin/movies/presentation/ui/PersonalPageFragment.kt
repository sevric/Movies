package com.severin.movies.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.severin.movies.R
import com.severin.movies.databinding.FragmentPersonalPageBinding
import com.severin.movies.presentation.vm.FirebaseAuthorizationViewModel
import com.severin.movies.presentation.vm.FirebaseDBViewModel

class PersonalPageFragment : Fragment() {

    private var _binding: FragmentPersonalPageBinding? = null
    private val binding get() = _binding!!

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

    companion object {
        private const val DEFAULT_EMPTY_EMAIL_STRING = ""
        private const val STRING_WITH_NAME_AND_LAST_NAME_TO_FORMAT = "%s %s"

        @JvmStatic
        fun newInstance() =
            PersonalPageFragment()
    }
}