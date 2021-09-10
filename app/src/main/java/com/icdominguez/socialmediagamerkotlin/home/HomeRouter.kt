package com.icdominguez.socialmediagamerkotlin.home

import android.content.Context
import android.content.Intent
import com.google.android.datatransport.runtime.scheduling.jobscheduling.SchedulerConfig
import com.icdominguez.socialmediagamerkotlin.base.BaseActivityRouter

class HomeRouter : BaseActivityRouter {
    override fun intent(activity: Context): Intent = Intent(activity, HomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
}