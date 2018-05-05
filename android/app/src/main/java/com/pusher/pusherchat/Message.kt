package com.pusher.pusherchat

data class Message(var user:String,
                   var message:String,
                   var time:Long)