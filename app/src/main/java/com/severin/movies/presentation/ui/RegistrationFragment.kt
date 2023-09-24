package com.severin.movies.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.severin.movies.R
import com.severin.movies.databinding.FragmentRegistrationBinding
import com.severin.movies.presentation.MovieApplicationGlobal
import com.severin.movies.presentation.vm.RegistrationViewModel

class RegistrationFragment : Fragment() {
    private var emailToResetPassword: String? = null

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        (requireActivity().application as MovieApplicationGlobal).component
    }

    private val registrationViewModel: RegistrationViewModel by lazy {
        ViewModelProvider(
            requireActivity()
        )[RegistrationViewModel::class.java]
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
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateViews()
        setClickListener()
        observeRegistrationViewModel()
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
                NO_EMAIL_PASSED_NOTIFICATION,
                Toast.LENGTH_SHORT
            ).show()//TODO(replace the Toast by an appearing an AlertFragment)
            activity?.supportFragmentManager?.popBackStack()//TODO(make the fragment close and back to the previous frag)
        } else if (!(args.containsKey(EMAIL_TO_REGISTER_STR_KEY))) {
            Toast.makeText(
                this.requireContext(),
                REQUEST_PROVIDE_EMAIL_NOTIFICATION,
                Toast.LENGTH_SHORT
            ).show()//TODO(replace the Toast by an appearing an AlertFragment)
            activity?.supportFragmentManager?.popBackStack()
            //TODO(make the fragment close and back to the previous frag)
        } else {
            emailToResetPassword = args.getString(EMAIL_TO_REGISTER_STR_KEY)
        }
    }

    private fun initiateViews() {
        binding.inputEmailOrLogin.setText(emailToResetPassword)
    }

    private fun setClickListener() {
        binding.btnRegister.setOnClickListener {
            val emailString = binding.inputEmailOrLogin.text.toString()
            val passwordString = binding.inputPassword.text.toString()
            val nameString = binding.inputName.text.toString()
            val lastNameString = binding.inputLastName.text.toString()
            val ageString = binding.inputAge.text.toString()

            if (emailString.isNotEmpty()
                || emailString != EMPTY_STRING
                || passwordString.isNotEmpty()
                || passwordString != EMPTY_STRING
                || nameString.isNotEmpty()
                || nameString != EMPTY_STRING
                || lastNameString.isNotEmpty()
                || lastNameString != EMPTY_STRING
                || ageString.isNotEmpty()
                || ageString != EMPTY_STRING
            ) {
                registrationViewModel.signUp(
                    emailString,
                    passwordString,
                    nameString,
                    lastNameString,
                    Integer.parseInt(ageString)
                )
            } else {
                Toast.makeText(
                    requireActivity(),
                    REQUEST_FILL_FIELDS_NOTIFICATION,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun observeRegistrationViewModel() {
        registrationViewModel.error.observe(this) {
            if (it != null) {
                Toast.makeText(
                    requireActivity(),
                    it,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        registrationViewModel.user.observe(this) {
            if (it != null) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.main_navigation_view,
                        PersonalPageFragment.newInstance()
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    companion object {
        private const val EMAIL_TO_REGISTER_STR_KEY = "emailToResetStr"
        private const val NO_EMAIL_PASSED_NOTIFICATION = "No email were passed"
        private const val REQUEST_PROVIDE_EMAIL_NOTIFICATION = "Provide your email"
        private const val EMPTY_STRING = ""
        private const val REQUEST_FILL_FIELDS_NOTIFICATION =
            "The required fields should be filled!"

        @JvmStatic
        fun newInstance(emailToResetPassword: String) =
            RegistrationFragment().apply {
                arguments = Bundle().apply {
                    putString(EMAIL_TO_REGISTER_STR_KEY, emailToResetPassword)
                }
            }
    }
}