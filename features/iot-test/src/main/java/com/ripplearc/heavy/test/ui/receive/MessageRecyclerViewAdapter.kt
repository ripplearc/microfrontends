package com.ripplearc.heavy.test.ui.receive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ripplearc.heavy.iot.test.R
import com.ripplearc.heavy.test.model.MessageItem

internal class MessageRecyclerViewAdapter constructor(private var messages: List<MessageItem>) :
    RecyclerView.Adapter<MessageRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        LayoutInflater.from(parent.context)?.inflate(R.layout.message_item, parent, false)
            ?.let { ViewHolder(it) } ?: throw Exception("Cannot create ViewHolder")

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        messages.getOrNull(position)?.let {
            holder.message?.text = it.body
        }
    }

    fun updateMessageItems(newMessages: List<MessageItem>) =
        MessageItemDiffCallback(messages, newMessages).let {
            messages = newMessages
            DiffUtil.calculateDiff(it)
        }.run {
            dispatchUpdatesTo(this@MessageRecyclerViewAdapter)
        }

    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var message: TextView? = itemView.findViewById(R.id.message_body)
    }

}