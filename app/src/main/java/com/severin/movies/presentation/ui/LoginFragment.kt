package com.severin.movies.presentation.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.severin.movies.R
import com.severin.movies.databinding.FragmentLoginBinding
import com.severin.movies.presentation.MovieApplicationGlobal
import com.severin.movies.presentation.vm.FirebaseAuthorizationViewModel

class LoginFragment : Fragment() {
    private var emailToLogin: String? = null

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var emailString: String
    private lateinit var passwordString: String

    private val component by lazy {
        (requireActivity().application as MovieApplicationGlobal).component
    }

    private val firebaseAuthorizationViewModel: FirebaseAuthorizationViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
        )[FirebaseAuthorizationViewModel::class.java]
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
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()
        observeLoginViewModel()
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
                NOTIFICATION_NO_EMAIL,
                Toast.LENGTH_SHORT
            ).show()//TODO(replace the Toast by an appearing an AlertFragment)
            activity?.supportFragmentManager?.popBackStack()//TODO(make the fragment close and back to the previous frag)
        } else if (!(args.containsKey(EMAIL_TO_LOGIN_STR_KEY))) {
            Toast.makeText(
                this.requireContext(),
                REQUEST_PROVIDE_EMAIL,
                Toast.LENGTH_SHORT
            ).show()//TODO(replace the Toast by an appearing an AlertFragment)
            activity?.supportFragmentManager?.popBackStack()//TODO(make the fragment close and back to the previous frag)
        } else {
            emailToLogin = args.getString(EMAIL_TO_LOGIN_STR_KEY)
        }
    }

    private fun setClickListeners() {
        binding.btnLogin.setOnClickListener {
            emailString = getLoginStringFromView()
            passwordString = binding.inputPassword.text.toString()

            if (emailString.isNotEmpty()
                || emailString != EMPTY_STRING
                || passwordString.isNotEmpty()
                || passwordString != EMPTY_STRING
            ) {
                firebaseAuthorizationViewModel.login(emailString, passwordString)
            } else {
                Toast.makeText(
                    requireActivity(),
                    REQUEST_FILL_FIELDS_NOTIFICATION,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.tvForgotPassword.setOnClickListener {
            emailString = getLoginStringFromView()

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    R.id.main_navigation_view,
                    ResetPasswordFragment.newInstance(
                        emailString
                    )
                )
                .addToBackStack(null)
                .commit()
        }

        binding.tvRegisterLink.setOnClickListener {
            emailString = getLoginStringFromView()

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    R.id.main_navigation_view,
                    RegistrationFragment.newInstance(
                        emailString
                    )
                )
                .addToBackStack(null)
                .commit()
        }
    }

    private fun getLoginStringFromView(): String {
        return binding.inputEmailOrLogin.text.toString()
    }

    private fun observeLoginViewModel() {
        firebaseAuthorizationViewModel.error.observe(this) {
            if (it != null) {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
            }
        }

        firebaseAuthorizationViewModel.currentFirebaseAuthUser.observe(this) {
            if (it != null) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.main_navigation_view,
                        PersonalPageFragment.newInstance()
                    )
                    .addToBackStack(PERSONAL_PAGE_FRAGMENT_BACKSTACK_NAME)
                    .commit()
            }
        }
    }

    companion object {
        private const val EMAIL_TO_LOGIN_STR_KEY = "emailToResetStr"
        private const val NOTIFICATION_NO_EMAIL = "No email were entered"
        private const val REQUEST_PROVIDE_EMAIL = "Provide your email"
        private const val EMPTY_STRING = ""
        private const val REQUEST_FILL_FIELDS_NOTIFICATION =
            "The required fields should be filled!"
        private const val PERSONAL_PAGE_FRAGMENT_BACKSTACK_NAME = "PersonalPageFragment"

        @JvmStatic
        fun newInstance(emailToLogin: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(EMAIL_TO_LOGIN_STR_KEY, emailToLogin)
                }
            }
    }
}