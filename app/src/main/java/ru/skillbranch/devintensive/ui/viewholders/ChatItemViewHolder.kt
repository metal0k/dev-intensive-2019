package ru.skillbranch.devintensive.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import ru.skillbranch.devintensive.models.data.ChatItem

abstract class ChatItemViewHolder(containerView: View): RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    abstract fun bind(item: ChatItem, listener: (ChatItem) -> Unit)
}