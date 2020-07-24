package ru.skillbranch.devintensive.ui.viewholders

import android.view.View
import kotlinx.android.synthetic.main.item_chat_archive.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.ChatItem

class ArchiveViewHolder(override val containerView: View):
    ChatItemViewHolder(containerView){

    override fun bind(item: ChatItem, listener: (ChatItem) -> Unit) {
//            iv_avatar_group.setInitials(item.title[0].toString())

//            sv_indicator.visibility = if (item.isOnline) View.VISIBLE else View.GONE
        tv_title_archive.text = itemView.resources.getString(R.string.title_archive)
        with(tv_message_author_archive) {
            visibility = if (item.author != null) android.view.View.VISIBLE else android.view.View.GONE
            text = item.author
        }
        with(tv_message_archive) {
            visibility = if (item.messageCount > 0) android.view.View.VISIBLE else android.view.View.GONE
            text = item.messageCount.toString()
        }
        tv_message_archive.text = item.shortDescription ?: itemView.resources.getString(
            R.string.no_messages_title
        )

        with(tv_date_archive) {
            visibility = if (item.lastMessageDate == null) android.view.View.GONE else android.view.View.VISIBLE
            text = item.lastMessageDateShort
        }

        with(tv_counter_archive) {
            visibility = if (item.messageCount > 0) android.view.View.VISIBLE else android.view.View.GONE
            text = item.messageCount.toString()
        }

        itemView.setOnClickListener { listener(item) }
    }
}