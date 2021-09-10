package com.icdominguez.socialmediagamerkotlin.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.icdominguez.socialmediagamerkotlin.R
import com.icdominguez.socialmediagamerkotlin.databinding.FragmentFilterBinding
import com.icdominguez.socialmediagamerkotlin.databinding.FragmentProfileBinding
import com.icdominguez.socialmediagamerkotlin.filter.FilterViewModel

class ProfileFragment : Fragment() {

    private lateinit var _binding: FragmentProfileBinding
    private val binding get() = _binding

    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        viewModel.getPostNumber()
        viewModel.getUser()

        setUp()
    }

    private fun setUp() {
        viewModel.getPostNumberStatus.observe(viewLifecycleOwner, Observer { result ->
            binding.editTextPostNumber.text = result.toString()
        })

        viewModel.getUserStatus.observe(viewLifecycleOwner, Observer { result ->
            binding.editTextEmail.text = result["email"]
            binding.editTextPhone.text = result["phone"]
            binding.editTextUsername.text = result["username"]
        })
    }
}