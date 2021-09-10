package com.icdominguez.socialmediagamerkotlin.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.icdominguez.socialmediagamerkotlin.R
import com.icdominguez.socialmediagamerkotlin.adapters.PostAdapter
import com.icdominguez.socialmediagamerkotlin.databinding.FragmentHomeBinding
import com.icdominguez.socialmediagamerkotlin.login.LoginRouter
import com.icdominguez.socialmediagamerkotlin.newPost.NewPostRouter
import com.icdominguez.socialmediagamerkotlin.postdetail.PostDetailActivity
import com.icdominguez.socialmediagamerkotlin.provider.AuthProvider

class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    private lateinit var viewModel: HomeViewModel
    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        setUp()
    }

    private fun setUp() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.app_bar_log_out -> {
                    viewModel.logOut()
                    LoginRouter().launch(requireActivity())
                    true
                } else -> false
            }
        }

        binding.fabAddPost.setOnClickListener {
            NewPostRouter().launch(requireContext())
        }

        //viewModel.getPosts()

        postAdapter = PostAdapter(viewModel.getAllPosts(), requireContext(), onLike, onPostClick, AuthProvider().getUid()!!)
        binding.recyclerView.adapter = postAdapter
        postAdapter.notifyDataSetChanged()
        postAdapter.startListening()
    }

    private val onLike: (postId: String, liked: Boolean) -> Unit = {
            postId, liked -> viewModel.likePost(postId!!, liked)
        postAdapter.notifyDataSetChanged()
    }

    private val onPostClick: (userId: String) -> Unit = { userId ->
        var intent = Intent(context, PostDetailActivity::class.java)
        intent.putExtra("userId", userId)
        startActivity(intent)
    }
}