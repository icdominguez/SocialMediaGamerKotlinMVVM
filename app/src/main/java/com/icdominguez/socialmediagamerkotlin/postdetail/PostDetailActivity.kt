package com.icdominguez.socialmediagamerkotlin.postdetail

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.slider.Slider
import com.icdominguez.socialmediagamerkotlin.adapters.SliderAdapter
import com.icdominguez.socialmediagamerkotlin.databinding.ActivityPostDetailBinding
import com.icdominguez.socialmediagamerkotlin.model.SliderItem
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView

class PostDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostDetailBinding
    private lateinit var viewModel: PostDetailViewModel

    private lateinit var sliderAdapter: SliderAdapter

    private var list = ArrayList<SliderItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(PostDetailViewModel::class.java)

        viewModel.getPostByPost(intent.getStringExtra("userId")!!)

        viewModel.getPostStatus.observe(this, Observer { result ->
            if(result.image1 != null) {
                list.add(SliderItem(result.image1!!, 0))
            }
            if(result.image2 != null) {
                list.add(SliderItem(result.image2!!, 0))
            }
            if(result.title != null) {
                binding.textViewVideogameTitle.text = result.title
            }
            if(result.category != null) {
                binding.textViewVideogameDescription.text = result.category
            }
            instanciateSliderAdapter()
        })

    }

    private fun instanciateSliderAdapter() {
        sliderAdapter = SliderAdapter(applicationContext, list)
        binding.sliderView.setSliderAdapter(sliderAdapter)
        binding.sliderView.setIndicatorAnimation(IndicatorAnimationType.THIN_WORM)
        binding.sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        binding.sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
        binding.sliderView.indicatorSelectedColor = Color.WHITE
        binding.sliderView.indicatorUnselectedColor = Color.GRAY
        binding.sliderView.scrollTimeInSec = 3
        binding.sliderView.startAutoCycle()
    }
}