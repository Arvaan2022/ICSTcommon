package com.icst.commonmodule.app

import android.app.Application

class App:Application(){

    companion object{
        private var appointmnetAdded: Boolean = false
        lateinit var instance: App
        var url : String = ""
        var language =""
        var token =""


        fun getAppointmentAdd(): Boolean {
            return appointmnetAdded
        }
        fun setAppointmentAdd(appointmnet_added: Boolean) {
            appointmnetAdded = appointmnet_added
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}