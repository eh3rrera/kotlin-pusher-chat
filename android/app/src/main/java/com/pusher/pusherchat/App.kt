package com.pusher.pusherchat

import android.app.Application

class App:Application() {
    companion object {
        lateinit var user:String
    }
}