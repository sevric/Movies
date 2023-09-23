package com.severin.movies.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.severin.movies.databinding.FragmentPersonalPageBinding

class PersonalPageFragment : Fragment() {

    private var _binding: FragmentPersonalPageBinding? = null
    private val binding get() = _binding!!

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
        //TODO(observe authorized user of firebase authorization view model
        // and launch LoginFragment if the user is  null)

        //TODO(observe authorized user of  firebase users data base view model
        // and update the text field with the user name and last name)

        //TODO(make adding watch later movies from the firebase DB)
    }

    private fun prepareClickListener() {
        binding.ivBtnLogoutHidden.setOnClickListener {//TODO(change to other btn (hidden in the menu by click on the current btn))
            //TODO(call logging out of firebase authorization view model)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PersonalPageFragment()
    }
}