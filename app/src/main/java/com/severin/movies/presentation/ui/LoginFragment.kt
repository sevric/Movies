package com.severin.movies.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.severin.movies.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var emailToLogin: String? = null

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var emailString: String
    private lateinit var passwordString: String

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

    companion object {
        private const val EMAIL_TO_LOGIN_STR_KEY = "emailToResetStr"
        private const val NOTIFICATION_NO_EMAIL = "No email were entered"
        private const val REQUEST_PROVIDE_EMAIL = "Provide your email"

        @JvmStatic
        fun newInstance(emailToLogin: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(EMAIL_TO_LOGIN_STR_KEY, emailToLogin)
                }
            }
    }
}