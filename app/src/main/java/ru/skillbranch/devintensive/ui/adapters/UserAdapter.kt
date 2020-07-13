package ru.skillbranch.devintensive.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user_list.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.extensions.show
import ru.skillbranch.devintensive.models.data.UserItem

class UserAdapter(val listener: (UserItem)->Unit): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    var items: List<UserItem> = listOf()

    fun updateData(data: List<UserItem>) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserViewHolder(inflater.inflate(R.layout.item_user_list, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) = holder.bind(items[position], listener)

    inner class UserViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(user: UserItem, listener: (UserItem) -> Unit) {
            if (user.avatar != null) {
                Glide.with(containerView)
                    .load(user.avatar)
                    .into(iv_avatar_user)
            } else {
                Glide.with(containerView).clear(iv_avatar_user)
                iv_avatar_user.setInitials(user.initials ?: "??")
            }

            sv_indicator.show(user.isOnline)
            tv_user_name.text = user.fullName
            tv_last_activity.text = user.lastActivity
            iv_selected.show(user.isSelected)
            itemView.setOnClickListener{listener(user)}
        }

    }

}