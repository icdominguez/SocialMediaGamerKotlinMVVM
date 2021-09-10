package com.icdominguez.socialmediagamerkotlin.provider

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class UserProvider {

    var collection: CollectionReference = FirebaseFirestore.getInstance().collection("users")

    fun getUser(userId: String) : Task<DocumentSnapshot> {
        return collection.document(userId).get()
    }

}