package com.icdominguez.socialmediagamerkotlin.postdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.icdominguez.socialmediagamerkotlin.model.Post
import com.icdominguez.socialmediagamerkotlin.model.User
import com.icdominguez.socialmediagamerkotlin.provider.PostProvider
import com.icdominguez.socialmediagamerkotlin.provider.UserProvider
import com.squareup.picasso.Picasso

class PostDetailViewModel : ViewModel() {

    private var _getPostStatus = MutableLiveData<Post>()
    var getPostStatus: MutableLiveData<Post> = _getPostStatus

    private var _getUserStatus = MutableLiveData<User>()
    var _getUserStattus : MutableLiveData<User> = _getUserStatus

    var post = Post()
    var user = User()


    fun getPostByPost(postId: String) {

        PostProvider().getPostById(postId).addOnSuccessListener { documentSnapshot->
            if(documentSnapshot.exists()) {
                if(documentSnapshot.contains("image1")) {
                    post.image1 = documentSnapshot.getString("image1")!!
                }
                if(documentSnapshot.contains("image2")) {
                    post.postId = documentSnapshot.getString("image2")!!
                }
                if(documentSnapshot.contains("title")) {
                    post.title = documentSnapshot.getString("title")!!
                }
                if(documentSnapshot.contains("postId")) {
                    post.postId = documentSnapshot.getString("postId")
                }

                getPostStatus.postValue(post)
            }
        }

    }

    fun getUserInfoById(userId: String) {
        UserProvider().getUser(userId).addOnSuccessListener { documentSnapshot ->
            if(documentSnapshot.exists()) {
                if(documentSnapshot.contains("username")) {
                    user.username = documentSnapshot.getString("username")
                }
                if(documentSnapshot.contains("phone")) {
                    user.phone = documentSnapshot.getString("phone")
                }
                if(documentSnapshot.contains("imageProfile")) {
                    var imageProfile = documentSnapshot.getString("imageProfile")

                    if(imageProfile != null && imageProfile.isNotEmpty()) {
                        user.imageProfile = imageProfile
                    }
                    
                }
            }
        }
    }

}