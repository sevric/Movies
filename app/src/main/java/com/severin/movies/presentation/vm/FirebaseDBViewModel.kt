package com.severin.movies.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.severin.movies.data.model.MoviesWatcher

class FirebaseDBViewModel : ViewModel() {
    val currentFirebaseDBUser = MutableLiveData<MoviesWatcher>()
    private val firebaseDB = FirebaseDatabase.getInstance()

    private val auth = FirebaseAuth.getInstance().apply {
        this.addAuthStateListener {
            firebaseDB.getReference(DB_USERS_TABLE_NAME)
                .apply {
                    val authUserId = it.uid
                    if (authUserId != null) {
                        child(authUserId).addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                currentFirebaseDBUser.postValue(
                                    snapshot.getValue<MoviesWatcher>()
                                )
                            }

                            override fun onCancelled(error: DatabaseError) {
                                //TODO("Not yet designed what to do here")
                            }
                        })
                    } else {
                        //TODO("Make Dialog Message")
                    }
                }
        }
    }

    companion object {
        private const val DB_USERS_TABLE_NAME= "Users"
    }
}