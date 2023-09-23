package com.severin.movies.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ResetPasswordViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance().apply {
        this.addAuthStateListener {
            if (it.currentUser != null) {
                user.postValue(it.currentUser)
            }
        }
    }

    val error = MutableLiveData<String>()
    val user = MutableLiveData<FirebaseUser>()

    val isSuccessful = MutableLiveData<Boolean>()

    fun resetPassword(emailString: String) {
        auth.sendPasswordResetEmail(emailString)
            .addOnSuccessListener {
                isSuccessful.postValue(RESET_EMAIL_SUCCESSFULLY_SENT_FLAG)
            }
            .addOnFailureListener {
                error.postValue(it.message)
            }
    }

    companion object {
        private const val RESET_EMAIL_SUCCESSFULLY_SENT_FLAG = true
    }
}