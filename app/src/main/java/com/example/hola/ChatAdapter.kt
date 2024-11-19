package com.example.hola


import android.os.Build
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val messages: List<message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_SENDER = 1
    private val TYPE_RECEIVER = 2

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isSender) {
            TYPE_SENDER
        } else TYPE_RECEIVER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_SENDER -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.chatting_sender_chat_bubble, parent, false)
                SenderViewHolder(itemView)
            }
            TYPE_RECEIVER -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.chatting_reciever_chat_bubble, parent, false)
                ReceiverViewHolder(itemView)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        if (holder is SenderViewHolder) {
            holder.messageText.text = message.text
            holder.timestamp.text = message.timestamp
        } else if (holder is ReceiverViewHolder) {
            holder.messageText.text = message.text
            holder.timestamp.text = message.timestamp
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    class SenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageText: TextView = itemView.findViewById(R.id.messageText)
        val timestamp: TextView = itemView.findViewById(R.id.timestamp)
    }

    class ReceiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageText: TextView = itemView.findViewById(R.id.messageText)
        val timestamp: TextView = itemView.findViewById(R.id.timestamp)
    }
}
