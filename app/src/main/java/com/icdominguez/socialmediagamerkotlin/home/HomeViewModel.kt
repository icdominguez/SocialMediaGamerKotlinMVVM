package com.icdominguez.socialmediagamerkotlin.home

import androidx.lifecycle.ViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.icdominguez.socialmediagamerkotlin.model.Post
import com.icdominguez.socialmediagamerkotlin.provider.AuthProvider
import com.icdominguez.socialmediagamerkotlin.provider.PostProvider

class HomeViewModel : ViewModel() {

    private var auth = AuthProvider()

    fun getAllPosts(): FirestoreRecyclerOptions<Post> {
        var query: Query = PostProvider().getAll()
        return FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()
    }

    fun likePost(post: String, liked: Boolean) {
        if(liked) {
            PostProvider().deleteLike(post, auth.getUid()!!)
        } else {
            PostProvider().addlike(post, auth.getUid()!!)
        }
    }

    fun logOut() {
        auth.logOut()
    }
}