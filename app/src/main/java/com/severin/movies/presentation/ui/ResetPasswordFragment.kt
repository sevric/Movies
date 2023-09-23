package com.severin.movies.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.severin.movies.R
import com.severin.movies.databinding.FragmentResetPasswordBinding
import com.severin.movies.presentation.vm.ResetPasswordViewModel

class ResetPasswordFragment : Fragment() {

    private var emailToResetStr: String? = null

    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!

    private val resetPasswordViewModel: ResetPasswordViewModel by lazy {
        ViewModelProvider(
            requireActivity()
        )[ResetPasswordViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parseArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateViews()
        setClickListeners()
        observeResetPasswordViewModel()
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
        } else if (!(args.containsKey(EMAIL_TO_RESET_STR_KEY))) {
            Toast.makeText(
                this.requireContext(),
                REQUEST_PROVIDE_EMAIL_NOTIFICATION,
                Toast.LENGTH_SHORT
            ).show()//TODO(replace the Toast by an appearing an AlertFragment)
            activity?.supportFragmentManager?.popBackStack()
            //TODO(make the fragment close and back to the previous frag)
        } else {
            emailToResetStr = args.getString(EMAIL_TO_RESET_STR_KEY)
        }
    }

    private fun initiateViews() {
        binding.inputEmailResetPassword.setText(emailToResetStr)
    }

    private fun setClickListeners() {
        binding.btnResetPassword.setOnClickListener {
            val emailString = binding.inputEmailResetPassword.text.toString()
            resetPasswordViewModel.resetPassword(emailString)
        }
        binding.btnBackToLogin.setOnClickListener {
            val emailString = binding.inputEmailResetPassword.text.toString()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    R.id.main_navigation_view,
                    LoginFragment.newInstance(
                        emailString
                    )
                )
                .addToBackStack(null)
                .commit()
        }
    }

    private fun observeResetPasswordViewModel() {
        resetPasswordViewModel.error.observe(this) {
            if (it != null) {
                Toast.makeText(
                    requireActivity(),
                    it,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        resetPasswordViewModel.isSuccessful.observe(this) {
            Toast.makeText(
                requireActivity(),
                LINK_SENT_NOTIFICATION,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    companion object {
        private const val EMAIL_TO_RESET_STR_KEY = "emailToResetStr"
        private const val NO_EMAIL_PASSED_NOTIFICATION = "No email were passed"
        private const val REQUEST_PROVIDE_EMAIL_NOTIFICATION = "Provide your email"
        private const val LINK_SENT_NOTIFICATION =
            "The reset link has been sent to the email. Check it."

        @JvmStatic
        fun newInstance(emailToResetPassword: String) =
            ResetPasswordFragment().apply {
                arguments = Bundle().apply {
                    putString(EMAIL_TO_RESET_STR_KEY, emailToResetPassword)
                }
            }
    }
}