package com.ripplearc.heavy.test.ui.receive

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ripplearc.heavy.test.model.MessageItem

class MessageRecyclerViewAdapter : RecyclerView.Adapter<MessageRecyclerViewAdapter.ViewHolder>() {

    private val messages: List<MessageItem> = emptyList()


    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var message: TextView

        init {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


}