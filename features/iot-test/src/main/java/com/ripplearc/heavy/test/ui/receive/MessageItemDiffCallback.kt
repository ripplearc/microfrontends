package com.ripplearc.heavy.test.ui.receive

import androidx.recyclerview.widget.DiffUtil
import com.ripplearc.heavy.test.model.MessageItem

internal class MessageItemDiffCallback(
	private val oldMessageItems: List<MessageItem>,
	private val newMessageItems: List<MessageItem>
) : DiffUtil.Callback() {
	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
		oldMessageItems.getOrNull(oldItemPosition) == newMessageItems.getOrNull(newItemPosition)

	override fun getOldListSize(): Int = oldMessageItems.size

	override fun getNewListSize(): Int = newMessageItems.size

	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
		oldMessageItems.getOrNull(oldItemPosition) == newMessageItems.getOrNull(newItemPosition)

}
