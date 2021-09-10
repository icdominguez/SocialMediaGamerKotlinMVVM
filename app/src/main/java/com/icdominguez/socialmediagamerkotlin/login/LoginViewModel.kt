package com.icdominguez.socialmediagamerkotlin.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdominguez.socialmediagamerkotlin.common.Constants
import com.icdominguez.socialmediagamerkotlin.common.ResultOf
import com.icdominguez.socialmediagamerkotlin.home.HomeRouter
import com.icdominguez.socialmediagamerkotlin.provider.AuthProvider
import com.icdominguez.socialmediagamerkotlin.provider.TokenProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private var auth = AuthProvider()
    private var tokenProvider = TokenProvider()

    private val _loginStatus = MutableLiveData<ResultOf<String>>()
    val loginStatus : LiveData<ResultOf<String>> = _loginStatus

    private val _tokenStatus = MutableLiveData<ResultOf<String>>()
    val tokenStatus : LiveData<ResultOf<String>> = _tokenStatus

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                auth.login(email, password).addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        _loginStatus.postValue(ResultOf.Success(Constants.LOGIN_OK))
                        createToken()
                    } else {
                        _loginStatus.postValue(ResultOf.Success("${task.exception}"))
                    }
                }
            } catch(e: Exception) {
                _loginStatus.postValue(ResultOf.Failure("Error al hacer login ${e.message}", e))
            }
        }
    }

    fun createToken() {
        var userId = auth.getUid()
        if(userId != null) {

            viewModelScope.launch(Dispatchers.IO) {
                var errorCode = -1
                try {
                    tokenProvider.create().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            tokenProvider.saveToken(userId, task.result)
                            _tokenStatus.postValue(ResultOf.Success(Constants.TOKEN_CREATED))
                        } else {
                            _tokenStatus.postValue(ResultOf.Success("Error creating token ${task.exception}"))
                        }

                    }
                } catch (e: java.lang.Exception) {
                    if(errorCode != -1) {
                        _tokenStatus.postValue(ResultOf.Failure("Failed with Error Code $errorCode", e))
                    } else {
                        _tokenStatus.postValue(ResultOf.Failure("Failed with Exception Code ${e.message}", e))
                    }
                }
            }

        } else {

        }

    }

    fun checkUser() : Boolean {
        return auth.checkUser() != null
    }
}