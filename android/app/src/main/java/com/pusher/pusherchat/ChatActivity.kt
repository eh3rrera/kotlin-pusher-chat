package com.pusher.pusherchat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "ChatActivity"

class ChatActivity: AppCompatActivity() {
    private lateinit var adapter: MessageAdapter

    private val pusherAppKey = "PUSHER_APP_KEY"
    private val pusherAppCluster = "PUSHER_APP_CLUSTER"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        messageList.layoutManager = LinearLayoutManager(this)
        adapter = MessageAdapter(this)
        messageList.adapter = adapter

        btnSend.setOnClickListener {
            if(txtMessage.text.isNotEmpty()) {
                val message = Message(
                        App.user,
                        txtMessage.text.toString(),
                        Calendar.getInstance().timeInMillis
                )

                val call = ChatService.create().postMessage(message)

                call.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        resetInput()
                        if (!response.isSuccessful) {
                            Log.e(TAG, response.code().toString());
                            Toast.makeText(applicationContext,"Response was not successful", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        resetInput()
                        Log.e(TAG, t.toString());
                        Toast.makeText(applicationContext,"Error when calling the service", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(applicationContext,"Message should not be empty", Toast.LENGTH_SHORT).show()
            }
        }

        setupPusher()
    }

    private fun resetInput() {
        // Clean text box
        txtMessage.text.clear()

        // Hide keyboard
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun setupPusher() {
        val options = PusherOptions()
        options.setCluster(pusherAppCluster)

        val pusher = Pusher(pusherAppKey, options)
        val channel = pusher.subscribe("chat")

        channel.bind("new_message") { channelName, eventName, data ->
            val jsonObject = JSONObject(data)

            val message = Message(
                    jsonObject["user"].toString(),
                    jsonObject["message"].toString(),
                    jsonObject["time"].toString().toLong()
            )

            runOnUiThread {
                adapter.addMessage(message)
                // scroll the RecyclerView to the last added element
                messageList.scrollToPosition(adapter.itemCount - 1);
            }

        }

        pusher.connect()
    }
}