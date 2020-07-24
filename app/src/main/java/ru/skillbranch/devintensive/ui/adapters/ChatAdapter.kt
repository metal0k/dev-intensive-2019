package ru.skillbranch.devintensive.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.extensions.log
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.models.data.ChatType
import ru.skillbranch.devintensive.ui.viewholders.ArchiveViewHolder
import ru.skillbranch.devintensive.ui.viewholders.ChatItemViewHolder
import ru.skillbranch.devintensive.ui.viewholders.GroupViewHolder
import ru.skillbranch.devintensive.ui.viewholders.SingleViewHolder

open class ChatAdapter(val listener : (ChatItem)->Unit): RecyclerView.Adapter<ChatItemViewHolder>() {

    companion object {
        private const val SINGLE_TYPE = 0
        private const val GROUP_TYPE = 1
        private const val ARCHIVE_TYPE = 2

    }

    var items : List<ChatItem> = listOf()

    override fun getItemViewType(position: Int): Int = when(items[position].chatType) {
        ChatType.SINGLE -> SINGLE_TYPE
        ChatType.GROUP -> GROUP_TYPE
        ChatType.ARCHIVE -> ARCHIVE_TYPE
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            SINGLE_TYPE -> SingleViewHolder(
                inflater.inflate(R.layout.item_chat_single, parent, false)
            )
            GROUP_TYPE -> GroupViewHolder(
                inflater.inflate(R.layout.item_chat_group, parent, false)
            )
            ARCHIVE_TYPE -> ArchiveViewHolder(
                inflater.inflate(R.layout.item_chat_archive, parent, false)
            )
            else -> SingleViewHolder(
                inflater.inflate(R.layout.item_chat_single, parent, false)
            )
        }
//        val convertView = inflater.inflate(R.layout.item_chat_single, parent, false)
//        return SingleViewHolder(convertView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        this.log("onBindViewHolder $position")
        holder.bind(items[position], listener)
    }

    fun updateData(data: List<ChatItem>) {
        val diffCallback = object: DiffUtil.Callback() {
            override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean = items[oldPos].id == data[newPos].id

            override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean = items[oldPos].hashCode() == data[newPos].hashCode()

            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = data.size
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = data
        diffResult.dispatchUpdatesTo(this)
    }

}