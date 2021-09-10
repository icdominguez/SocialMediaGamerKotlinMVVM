package com.icdominguez.socialmediagamerkotlin.register

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.icdominguez.socialmediagamerkotlin.model.User
import com.icdominguez.socialmediagamerkotlin.common.Constants
import com.icdominguez.socialmediagamerkotlin.common.ResultOf
import com.icdominguez.socialmediagamerkotlin.R
import com.icdominguez.socialmediagamerkotlin.databinding.ActivityRegisterBinding
import com.shashank.sony.fancytoastlib.FancyToast

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        viewModel.registrationStatus.observe(this, Observer { result -> result?.let {
            when(it) {
                is ResultOf.Success -> {
                    if(it.value == Constants.USER_CREATED) {
                        FancyToast.makeText(this, "Registration successful user created", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show()
                    } else {
                        FancyToast.makeText(this, "Registration failed with ${it.value}", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                    }
                }

                is ResultOf.Failure -> {
                    val failedMessage = it.message ?: "Unknown Error"
                    Toast.makeText(this, "Registration failed with $failedMessage", Toast.LENGTH_SHORT).show()
                }
            }
        } })

        binding.buttonRegister.setOnClickListener {
            val email = binding.textEmail
            val password1 = binding.textPassword
            val password2 = binding.textPassword2
            val phone = binding.textPhone
            val username = binding.textUsername


            when {
                email.text.isNullOrEmpty() -> {
                    email.error = getString(R.string.register_empty_email)
                }
                password1.text.toString() != password2.text.toString() -> {
                    password2.error = getString(R.string.register_mismatches_passwords)
                }
                phone.text.isNullOrEmpty() -> {
                    phone.error = getString(R.string.register_empty_phone)
                }
                username.text.isNullOrEmpty() -> {
                    binding.textUsername.error = getString(R.string.register_empty_username)
                }
                else -> {
                    viewModel.createUser(email.text.toString(), password1.text.toString(), username.text.toString(), phone.text.toString())
                }
            }
        }
    }
}