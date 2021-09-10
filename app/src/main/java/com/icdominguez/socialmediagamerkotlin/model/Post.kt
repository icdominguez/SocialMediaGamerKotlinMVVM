package com.icdominguez.socialmediagamerkotlin.model

data class Post (
    var postId : String? = null,
    var title : String? = null,
    var description : String? = null,
    var image1 : String? = null,
    var image2 : String? = null,
    var userId : String? = null,
    var category : String? = null,
    var timestamp : Long = 0,
    var likes : List<String> = emptyList()
)