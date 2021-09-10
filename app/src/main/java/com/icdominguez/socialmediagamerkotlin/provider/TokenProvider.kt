package com.icdominguez.socialmediagamerkotlin.provider

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging

class TokenProvider {

    var collection: CollectionReference = FirebaseFirestore.getInstance().collection("tokens")

    fun create() : Task<String> {
        return FirebaseMessaging.getInstance().token
    }

    fun saveToken(userId: String, token: String?) {
        collection.document(userId).set(token!!)
    }
}