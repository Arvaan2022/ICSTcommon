package com.icst.commonmodule.app

import android.app.Application
import com.icst.commonmodule.R

class App:Application(){

    companion object{
        private var appointmnetAdded: Boolean = false

        var url : String = ""
        var language =""
        var token =""
        var appLogo:Int = R.drawable.ic_circle_tick


        fun getAppointmentAdd(): Boolean {
            return appointmnetAdded
        }
        fun setAppointmentAdd(appointmnet_added: Boolean) {
            appointmnetAdded = appointmnet_added
        }
    }

    override fun onCreate() {
        super.onCreate()

    }

}