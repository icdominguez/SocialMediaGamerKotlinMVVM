package com.icdominguez.socialmediagamerkotlin.newPost

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.icdominguez.socialmediagamerkotlin.camera.CameraXActivity
import com.icdominguez.socialmediagamerkotlin.common.Constants
import com.icdominguez.socialmediagamerkotlin.databinding.ActivityNewPostBinding
import com.icdominguez.socialmediagamerkotlin.model.Post
import com.squareup.picasso.Picasso
import java.io.File

class NewPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewPostBinding
    private lateinit var viewModel: NewPostViewModel
    private lateinit var category: String
    private var photoPhileImage1: String? = null
    private var photoFileImage2: String? = null

    companion object {
        const val REQUEST_CODE_IMAGE_1 = 1001
        const val REQUEST_CODE_IMAGE_2 = 1002

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(NewPostViewModel::class.java)

        setEvents()
    }

    private fun setEvents() {
        binding.imageViewPost1.setOnClickListener {
            openCamera(REQUEST_CODE_IMAGE_1)
        }

        binding.imageViewPost2.setOnClickListener {
            openCamera(REQUEST_CODE_IMAGE_2)
        }

        binding.imageViewPc.setOnClickListener {
            setCategory(1)
        }

        binding.imageViewPs4.setOnClickListener {
            setCategory(2)
        }

        binding.imageViewXbox.setOnClickListener {
            setCategory(3)
        }

        binding.imageViewNintendo.setOnClickListener {
            setCategory(4)
        }

        binding.buttonPost.setOnClickListener {
            savePost()
        }

        binding.circleImageBack.setOnClickListener {
            finish()
        }
    }

    private fun openCamera(imageView: Int) {
        var intent = Intent(this, CameraXActivity::class.java)
        startActivityForResult(intent, imageView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == REQUEST_CODE_IMAGE_1) {
                photoPhileImage1 = data!!.getStringExtra(Constants.INTENT_CAMARAX)
                Picasso.get().load(photoPhileImage1).into(binding.imageViewPost1)
            } else if(requestCode == REQUEST_CODE_IMAGE_2) {
                photoFileImage2 = data!!.getStringExtra(Constants.INTENT_CAMARAX)
                Picasso.get().load(photoFileImage2).into(binding.imageViewPost2)
            }
        }

    }

    private fun savePost() {
        var newPost = Post()

        newPost.title = binding.editTextVideoGame.text.toString()
        newPost.description = binding.editTextDescription.text.toString()
        newPost.category = category

        viewModel.addPost(newPost)
    }

    private fun setCategory(category: Int) {
        when(category) {
            1 -> this.category = "PC"
            2 -> this.category = "PS4"
            3 -> this.category = "XBOX"
            4 -> this.category = "NINTENDO"
        }

        binding.textViewCategory.text = this.category
    }
}