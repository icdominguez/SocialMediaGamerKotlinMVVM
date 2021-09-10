package com.icdominguez.socialmediagamerkotlin.newPost

import android.content.Context
import android.content.Intent
import com.icdominguez.socialmediagamerkotlin.base.BaseActivityRouter

class NewPostRouter : BaseActivityRouter {
    override fun intent(activity: Context): Intent = Intent(activity, NewPostActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
}