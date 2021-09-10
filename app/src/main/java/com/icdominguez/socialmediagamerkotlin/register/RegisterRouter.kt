package com.icdominguez.socialmediagamerkotlin.register

import android.content.Context
import android.content.Intent
import com.icdominguez.socialmediagamerkotlin.base.BaseActivityRouter

class RegisterRouter : BaseActivityRouter {
    override fun intent(activity: Context): Intent = Intent(activity, RegisterActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
}