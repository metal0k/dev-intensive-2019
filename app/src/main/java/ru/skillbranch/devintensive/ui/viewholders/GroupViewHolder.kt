package ru.skillbranch.devintensive.ui.viewholders

import android.graphics.Color
import android.view.View
import kotlinx.android.synthetic.main.item_chat_group.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.ui.adapters.ChatItemTouchHelperCallback

class GroupViewHolder(override val containerView: View):
    ChatItemViewHolder(containerView),
    ChatItemTouchHelperCallback.ItemTouchViewHolder{

    override fun bind(item: ChatItem, listener: (ChatItem) -> Unit) {
        iv_avatar_group.setInitials(item.title[0].toString())

        with(tv_date_group) {
            visibility = if (item.lastMessageDate == null) android.view.View.GONE else android.view.View.VISIBLE
            text = item.lastMessageDateShort
        }
        with(tv_counter_group) {
            visibility = if (item.messageCount > 0) android.view.View.VISIBLE else android.view.View.GONE
            text = item.messageCount.toString()
        }
        tv_title_group.text = item.title
        with(tv_message_author) {
            visibility = if (item.author != null) android.view.View.VISIBLE else android.view.View.GONE
            text = item.author
        }
        tv_message_group.text = item.shortDescription ?: itemView.resources.getString(
            R.string.no_messages_title
        )

        itemView.setOnClickListener { listener(item) }
    }

    override fun onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY)
    }

    override fun onItemCleared() {
        itemView.setBackgroundColor(Color.WHITE)
    }

}