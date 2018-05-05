package com.example.demo

import com.pusher.rest.Pusher
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/message")
class MessageController {
    private val pusher = Pusher("PUSHER_APP_ID", "PUSHER_APP_KEY", "PUSHER_APP_SECRET")

    init {
        pusher.setCluster("PUSHER_APP_CLUSTER")
    }

    @PostMapping
    fun postMessage(@RequestBody message: Message) : ResponseEntity<Unit> {
        pusher.trigger("chat", "new_message", message)
        return ResponseEntity.ok().build()
    }
}