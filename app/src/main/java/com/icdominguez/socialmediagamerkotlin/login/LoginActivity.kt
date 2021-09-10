package com.icdominguez.socialmediagamerkotlin.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.icdominguez.socialmediagamerkotlin.R
import com.icdominguez.socialmediagamerkotlin.common.Constants
import com.icdominguez.socialmediagamerkotlin.common.ResultOf
import com.icdominguez.socialmediagamerkotlin.databinding.ActivityLoginBinding
import com.icdominguez.socialmediagamerkotlin.home.HomeRouter
import com.icdominguez.socialmediagamerkotlin.register.RegisterRouter
import com.shashank.sony.fancytoastlib.FancyToast

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        setUp()
        setEvents()
    }

    private fun setUp() {
        viewModel.loginStatus.observe(this, Observer { result ->
            result?.let {
                when (it) {
                    is ResultOf.Success -> {
                        if (it.value == Constants.LOGIN_OK) {
                            FancyToast.makeText(this, "Login correcto", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show()
                            viewModel.createToken()
                            HomeRouter().launch(applicationContext)
                        } else {
                            FancyToast.makeText(this, "Login failed with ${it.value}", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                        }
                    }

                    is ResultOf.Failure -> {
                        val failedMessage = it.message ?: "Unknown Error"
                        FancyToast.makeText(this, "Login failed with $failedMessage", FancyToast.LENGTH_SHORT,FancyToast.ERROR, false).show()
                    }
                }
            }
        })

        viewModel.tokenStatus.observe(this, Observer { result -> result?.let{
            when(it) {
                is ResultOf.Success -> {
                    if(it.value == Constants.TOKEN_CREATED) {
                        FancyToast.makeText(this, "Token successfully created", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show()
                    } else {
                        FancyToast.makeText(this, "Token failed with ${it.value}", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                    }
                }

                is ResultOf.Failure -> {
                    val failedMessage = it.message ?: "Unknown Error"
                    FancyToast.makeText(this, "Token failed with $failedMessage", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                }
            }
        } })
    }

    private fun setEvents() {
        binding.textViewRegister.setOnClickListener {
            RegisterRouter().launch(applicationContext)
        }

        binding.buttonLogin.setOnClickListener {
            viewModel.login(
                binding.textEmail.text.toString(),
                binding.textPassword2.text.toString()
            )
        }
    }
}

