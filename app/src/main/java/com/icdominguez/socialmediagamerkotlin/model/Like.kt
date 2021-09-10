package com.icdominguez.socialmediagamerkotlin.model

import java.sql.Timestamp

data class Like(
    var likeId: String? = null,
    var postId: String? = null,
    var userId: String? = null,
    var timestamp: Long = 0
)