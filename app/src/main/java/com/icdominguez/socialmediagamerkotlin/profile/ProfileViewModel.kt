package com.icdominguez.socialmediagamerkotlin.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.icdominguez.socialmediagamerkotlin.provider.AuthProvider
import com.icdominguez.socialmediagamerkotlin.provider.PostProvider
import com.icdominguez.socialmediagamerkotlin.provider.UserProvider

class ProfileViewModel : ViewModel() {

    var authProvider = AuthProvider()
    var postProvider = PostProvider()

    private var _getPostNumberStatus = MutableLiveData<Int>()
    var getPostNumberStatus: LiveData<Int> = _getPostNumberStatus

    private var _getUserStatus = MutableLiveData<MutableMap<String, String>>()
    var getUserStatus: MutableLiveData<MutableMap<String, String>> = _getUserStatus

    fun getUser() {
        UserProvider().getUser(authProvider.getUid()!!).addOnSuccessListener { snapshot ->

            val user = mutableMapOf<String, String>()

            if(snapshot.exists()) {
                if(snapshot.contains("email")) {
                    user["email"] = snapshot.getString("email").toString()
                }
                if(snapshot.contains("username")) {
                    user["username"] = snapshot.getString("username").toString()
                }
                if(snapshot.contains("phone")) {
                    user["phone"] = snapshot.getString("phone").toString()
                }
                if(snapshot.contains("imageProfile")) {
                    user["imageProfile"] = snapshot.getString("imageProfile").toString()
                }
                if(snapshot.contains("imageCover")) {
                    user["imageCover"] = snapshot.getString("imageCover").toString()
                }

                getUserStatus.postValue(user)
            }
            else {

            }
        }
    }

    fun getPostNumber() {
        postProvider.getPostByUser(authProvider.getUid()!!).get().addOnSuccessListener{ snapshot ->
            _getPostNumberStatus.postValue(snapshot.size())
        }
    }
}