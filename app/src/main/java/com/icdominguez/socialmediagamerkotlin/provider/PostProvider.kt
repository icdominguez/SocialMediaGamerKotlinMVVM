package com.icdominguez.socialmediagamerkotlin.provider

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.icdominguez.socialmediagamerkotlin.model.Post
import org.w3c.dom.Document
import java.lang.reflect.Field

class PostProvider {
    var collection: CollectionReference = FirebaseFirestore.getInstance().collection("posts")

    fun getAll() : Query {
        return collection.orderBy("timestamp", Query.Direction.DESCENDING)
    }

    fun add(post: Post) : Task<Void> {
        var postId = collection.document().id
        post.postId = postId
        return collection.document().set(post)
    }

    fun addlike(postId: String, userId: String) {
        collection.document(postId).update("likes", FieldValue.arrayUnion(userId))
    }

    fun deleteLike(postId: String, userId: String) {
        collection.document(postId).update("likes", FieldValue.arrayRemove(userId))
    }

    fun getPostById(id: String) : Task<DocumentSnapshot> {
        return collection.document(id).get()
    }

    fun getPostByUser(id: String) : Query {
        return collection.whereEqualTo("userId", id)
    }

    fun getPostByTitle(title: String) : Query {
        return collection.orderBy("title").startAt(title).endAt(title + '\uf8ff')
    }

    fun deletePost(id: String) : Task<Void> {
        return collection.document(id).delete()
    }
}