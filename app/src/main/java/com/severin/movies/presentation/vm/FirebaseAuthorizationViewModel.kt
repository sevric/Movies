package com.severin.movies.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseAuthorizationViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance().apply {
        this.addAuthStateListener {
            if (it.currentUser != null) {
                currentFirebaseAuthUser.postValue(it.currentUser)
            } else {
                currentFirebaseAuthUser.postValue(null)
            }
        }
    }

    val currentFirebaseAuthUser = MutableLiveData<FirebaseUser?>()
    val error = MutableLiveData<String>()

    fun login(login: String, password: String) {
        auth.signInWithEmailAndPassword(login, password)
            .addOnSuccessListener {
                currentFirebaseAuthUser.postValue(it.user)
            }
            .addOnFailureListener {
                error.postValue(it.message)
            }
    }

    fun logout() {
        auth.signOut()
    }
}