package com.icdominguez.socialmediagamerkotlin.provider

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthProvider {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(email: String, password: String) : Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun register(email: String, password: String) : Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password);
    }

    fun getUid() : String? {
        return if(auth.currentUser != null) {
            auth.currentUser!!.uid
        } else {
            null
        }
    }

    fun logOut() {
        auth.signOut()
    }

    fun getEmail(): String? {
        return if(auth.currentUser != null) {
            auth.currentUser!!.email
        } else {
            null
        }
    }

    fun getUserSession() : FirebaseUser? {
        return if(auth.currentUser != null) {
            auth.currentUser!!
        } else {
            null
        }
    }

    fun checkUser() : FirebaseUser? {
        return auth.currentUser
    }

}