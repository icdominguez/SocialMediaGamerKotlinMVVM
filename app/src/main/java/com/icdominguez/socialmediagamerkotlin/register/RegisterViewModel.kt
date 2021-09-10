package com.icdominguez.socialmediagamerkotlin.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.icdominguez.socialmediagamerkotlin.common.Constants
import com.icdominguez.socialmediagamerkotlin.common.ResultOf
import com.icdominguez.socialmediagamerkotlin.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class RegisterViewModel : ViewModel() {

    private var auth: FirebaseAuth? = null
    private var storage: FirebaseFirestore? = null

    init {
        auth = FirebaseAuth.getInstance()
        storage = FirebaseFirestore.getInstance()
    }

    private val _registrationStatus = MutableLiveData<ResultOf<String>>()
    val registrationStatus : LiveData<ResultOf<String>> = _registrationStatus

    fun createUser(email: String, password: String, username: String, phone: String) {

        viewModelScope.launch(Dispatchers.IO) {
            var errorCode = -1

            try {
                auth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
                    if(task.isSuccessful) {

                        var user = User()
                        user.userId = auth!!.uid
                        user.username = username
                        user.email = email
                        user.phone = phone
                        user.timestamp = Date().time

                        storage!!.collection("users").document(user.userId!!).set(user).addOnCompleteListener { task: Task<Void> ->
                            if(task.isSuccessful) {
                                // TODO : Calll new Activity
                                _registrationStatus.postValue(ResultOf.Success(Constants.USER_CREATED))
                            } else {
                                _registrationStatus.postValue(ResultOf.Success("FiresbaseFirestore Failed with ${task.exception}"))
                            }
                        }

                    } else {
                        _registrationStatus.postValue(ResultOf.Success("FirebaseAuth Failed with ${task.exception}"))
                    }
                }
            } catch (e: Exception) {
                if(errorCode != -1) {
                    _registrationStatus.postValue(ResultOf.Failure("Failed with Error Code $errorCode", e))
                } else {
                    _registrationStatus.postValue(ResultOf.Failure("Failed with Exception Code ${e.message}", e))
                }
            }
        }

    }
}