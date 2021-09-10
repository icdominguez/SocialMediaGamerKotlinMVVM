package com.icdominguez.socialmediagamerkotlin.newPost

import androidx.lifecycle.ViewModel
import com.icdominguez.socialmediagamerkotlin.model.Post
import com.icdominguez.socialmediagamerkotlin.provider.AuthProvider
import com.icdominguez.socialmediagamerkotlin.provider.PostProvider

class NewPostViewModel : ViewModel() {

    var postProvider = PostProvider()
    var authProvider = AuthProvider()

    fun addPost(post: Post) {
        post.userId = authProvider.getUid()
        postProvider.add(post)
    }
}