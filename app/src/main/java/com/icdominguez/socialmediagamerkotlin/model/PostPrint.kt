package com.icdominguez.socialmediagamerkotlin.model

data class PostPrint (
    var numberLikes: Int = 0,
    var usermame : String = "",
    var title: String,
    var description: String,
    var image: String = "",
    var liked: Boolean,
    var timestamp:Long = 0
)