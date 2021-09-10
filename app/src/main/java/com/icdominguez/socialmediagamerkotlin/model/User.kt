package com.icdominguez.socialmediagamerkotlin.model

data class User (
    var userId: String? = null,
    var email: String? = null,
    var username: String? = null,
    var phone: String? = null,
    var imageProfile: String? = null,
    var imageCover: String? = null,
    var timestamp: Long? = null,
    var lastConnection: Long? = null,
    var online: Boolean = false
)