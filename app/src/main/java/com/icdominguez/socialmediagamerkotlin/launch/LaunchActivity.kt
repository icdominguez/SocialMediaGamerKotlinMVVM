package com.icdominguez.socialmediagamerkotlin.launch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.lifecycle.ViewModelProvider
import com.icdominguez.socialmediagamerkotlin.R
import com.icdominguez.socialmediagamerkotlin.databinding.ActivityLaunchBinding
import com.icdominguez.socialmediagamerkotlin.home.HomeRouter
import com.icdominguez.socialmediagamerkotlin.login.LoginRouter
import com.icdominguez.socialmediagamerkotlin.login.LoginViewModel
import com.icdominguez.socialmediagamerkotlin.provider.AuthProvider
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(AuthProvider().checkUser() != null) {
            HomeRouter().launch(applicationContext)
        } else {
            LoginRouter().launch(applicationContext)
        }
    }

}