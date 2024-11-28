package com.example.hola

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Define the adapter for accounts
class AccountAdapter(private val accountList: List<AccountsData>) :
    RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    // Define the ViewHolder for accounts
    class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val personImageView: ImageView = itemView.findViewById(R.id.imageViewAccount)
        val textView: TextView = itemView.findViewById(R.id.textViewAccountName)
    }

    // Inflate the layout for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.account_item, parent, false)
        return AccountViewHolder(view)
    }

    // Bind data to the ViewHolder
    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val account = accountList[position]
        holder.personImageView.setImageResource(account.imageResId)
        holder.textView.text = account.name
    }

    // Return the size of the list
    override fun getItemCount(): Int = accountList.size
}


