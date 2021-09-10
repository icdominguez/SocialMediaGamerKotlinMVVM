package com.icdominguez.socialmediagamerkotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.icdominguez.socialmediagamerkotlin.R
import com.icdominguez.socialmediagamerkotlin.adapters.PostAdapter.ViewHolder
import com.icdominguez.socialmediagamerkotlin.common.RelativeTime
import com.icdominguez.socialmediagamerkotlin.databinding.CardviewPostBinding
import com.icdominguez.socialmediagamerkotlin.model.Post
import com.squareup.picasso.Picasso
import java.util.*

class PostAdapter(options: FirestoreRecyclerOptions<Post>, ctx: Context, private val onLike: (postId: String, liked: Boolean) -> Unit, private val onPostClick: (userId: String) -> Unit, userId: String) : FirestoreRecyclerAdapter<Post, ViewHolder>(options) {

    var context: Context = ctx
    var userId = userId

    inner class ViewHolder(val binding : CardviewPostBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardviewPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, post: Post) {
        var liked = false

        var document = snapshots.getSnapshot(position)

        with(holder) {

            if(post.likes != null) {
                post.likes.forEach { user ->
                    if(user == userId) {
                        binding.imageViewLike.setImageResource(R.drawable.icon_like_blue)
                        liked = true
                    } else {
                        binding.imageViewLike.setBackgroundResource(R.drawable.ic_like_gray)
                    }
                }
                binding.textViewLikes.text = post.likes.size.toString()
            } else {
                binding.textViewLikes.text = "0"
            }

            binding.textViewTitlePostCard.text = post.title!!.uppercase(Locale.getDefault())
            binding.textViewDescriptionPostCard.text = post.description

            if(post.timestamp != null) {
                var timestamp: Long? = post.timestamp
                binding.textViewPostTimestamp.text = RelativeTime().getTimeAgo(timestamp!!, context)
            }

            if(post.image1 != null) {
                Picasso.get().load(post.image1).into(binding.imageViewPostCard)
            }

            itemView.setOnClickListener {
                onPostClick.invoke(document.id!!)
            }

            binding.imageViewLike.setOnClickListener{
                onLike.invoke(document.id, liked)
            }
        }
    }

}