package com.example.hola

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class chatting : AppCompatActivity() {
    @SuppressLint("WrongViewCast")


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatAdapter
    private val messages = mutableListOf< message>()



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chatting)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val rootView = findViewById<View>(R.id.main)
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.rootView.height
            val keyboardHeight = screenHeight - rect.bottom
            if (screenHeight * 0.15 < keyboardHeight) {
                // Keyboard is visible, adjust your layou

                val layout = findViewById<LinearLayout>(R.id.main1)
                val params = layout.layoutParams as ViewGroup.LayoutParams

                // Adjust height to account for the keyboard
                params.height = screenHeight - keyboardHeight
                layout.layoutParams = params






            } else {
                // Keyboard is hidden, reset the layout'

                val layout = findViewById<LinearLayout>(R.id.main1)
                val params = layout.layoutParams as ViewGroup.LayoutParams

                // Reset height to original (you may store this beforehand if needed)
                params.height = ViewGroup.LayoutParams.MATCH_PARENT
                layout.layoutParams = params
            }
        }



        val chatRecyclerView = findViewById<RecyclerView>(R.id.chatRecyclerView)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true  // To make new messages appear at the bottom
        chatRecyclerView.layoutManager = layoutManager

// Initialize your adapter with messages list
        val adapter = ChatAdapter(messages)
        chatRecyclerView.adapter = adapter







        val messageInput = findViewById<EditText>(R.id.messageInput)
        val micIcon = findViewById<ImageView>(R.id.mic)
        val imageIcon = findViewById<ImageView>(R.id.sendImage)
        val GifIcon = findViewById<ImageView>(R.id.sendGif)

        val sendButton = findViewById<ImageView>(R.id.sendButton)

        messageInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {

            }

            override fun onTextChanged(p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            )
            {

            }


            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.isNotEmpty()) {
                    micIcon.visibility = View.GONE
                    imageIcon.visibility= View.GONE
                    GifIcon.visibility= View.GONE
                    sendButton.visibility = View.VISIBLE

                    sendButton.setOnClickListener{
                        sendButton.setImageResource(R.drawable.chatting_send_button_light)

                        val messageText = messageInput.text.toString()
                        if (messageText.isNotEmpty()) {
                            val timestamp = System.currentTimeMillis().toString()
                            val message = message(messageText, timestamp, isSender = true)
                            messages.add(message)
                            adapter.notifyItemInserted(messages.size - 1)
                            chatRecyclerView.scrollToPosition(messages.size - 1)
                            messageInput.text.clear()
                        }



                    }



                } else {
                    micIcon.visibility = View.VISIBLE
                    GifIcon.visibility = View.VISIBLE
                    imageIcon.visibility = View.VISIBLE
                    sendButton.setImageResource(R.drawable.chatting_send_dark)
                    sendButton.visibility = View.GONE
                }
            }


        })




}

}
