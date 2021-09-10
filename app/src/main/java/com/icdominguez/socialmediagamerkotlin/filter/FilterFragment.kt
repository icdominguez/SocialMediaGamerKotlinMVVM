package com.icdominguez.socialmediagamerkotlin.filter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.icdominguez.socialmediagamerkotlin.R
import com.icdominguez.socialmediagamerkotlin.databinding.FragmentFilterBinding


class FilterFragment : Fragment() {

    private lateinit var _binding: FragmentFilterBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }
}