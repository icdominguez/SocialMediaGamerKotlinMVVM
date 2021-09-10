package com.icdominguez.socialmediagamerkotlin.provider

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.icdominguez.socialmediagamerkotlin.model.Like

class LikeProvider {
    var collection: CollectionReference = FirebaseFirestore.getInstance().collection("likes")

    fun create(like: Like) : Task<Void> {
        var document = collection.document()
        var likeId = document.id
        like.likeId = likeId
        return collection.document().set(like)
    }

    fun getLikeByPostAndUser(postId: String, userId: String) : Query {
        return collection.whereEqualTo("postId", postId).whereEqualTo("userId", userId)
    }

    fun getLikesByPost(postId: String) : Query {
        return collection.whereEqualTo("postId", postId)
    }

    fun deleteLike(likeId: String) {
        collection.document(likeId).delete()
    }
}