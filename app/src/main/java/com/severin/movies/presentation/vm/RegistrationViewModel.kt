package com.severin.movies.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.severin.movies.data.model.MoviesWatcher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class RegistrationViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance().apply {
        this.addAuthStateListener {
            if (it.currentUser != null) {
                user.postValue(it.currentUser)
            }
        }
    }

    val error = MutableLiveData<String>()
    val user = MutableLiveData<FirebaseUser>()

    private val firebaseDB by lazy {
        FirebaseDatabase.getInstance()
    }
    private val usersDBReference by lazy {
        firebaseDB.getReference(DB_USERS_TABLE_NAME)
    }

    fun signUp(
        email: String,
        password: String,
        name: String,
        lastName: String,
        age: Int
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val firebaseUser = it.user ?: return@addOnSuccessListener
                val newMessengerUser = MoviesWatcher(
                    firebaseUser.uid,
                    name,
                    lastName,
                    age
                )
                usersDBReference.child(newMessengerUser.id).setValue(newMessengerUser)
            }
            .addOnFailureListener {
                error.postValue(it.message)
            }
    }

    companion object {
        private const val DB_USERS_TABLE_NAME= "Users"
    }
}