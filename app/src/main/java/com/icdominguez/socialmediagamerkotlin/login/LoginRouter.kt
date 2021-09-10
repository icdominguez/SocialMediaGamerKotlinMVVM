package com.icdominguez.socialmediagamerkotlin.login

import android.content.Context
import android.content.Intent
import com.icdominguez.socialmediagamerkotlin.base.BaseActivityRouter
import com.icdominguez.socialmediagamerkotlin.register.RegisterActivity

class LoginRouter : BaseActivityRouter {
    override fun intent(activity: Context): Intent = Intent(activity, LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
}